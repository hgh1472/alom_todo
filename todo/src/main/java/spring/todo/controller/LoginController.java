package spring.todo.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.todo.domain.Member;
import spring.todo.exception.EmptyException;
import spring.todo.exception.ErrorConst;
import spring.todo.exception.ErrorResult;
import spring.todo.exception.WrongException;
import spring.todo.repository.member.LoginDto;
import spring.todo.repository.member.LoginOutputDto;
import spring.todo.security.JwtUtil;
import spring.todo.service.LoginService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final JwtUtil jwtUtil;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmptyException.class)
    public ErrorResult emptyExceptionHandler(EmptyException e) {
        log.error("[empty email or password]", e);
        return new ErrorResult(ErrorConst.EMPTY_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongException.class)
    public ErrorResult wrongExceptionHandler(WrongException e) {
        log.error("[Wrong email or password]", e);
        return new ErrorResult(ErrorConst.WRONG_EXCEPTION);
    }

    private final LoginService loginService;

    @PostMapping("/login")
    public LoginOutputDto login(@Valid @ModelAttribute LoginDto loginDto, BindingResult bindingResult, HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            throw new EmptyException(ErrorConst.EMPTY_EXCEPTION.getMessage());
        }

        Member loginMember = loginService.login(loginDto.getEmail(), loginDto.getPassword());
        if (loginMember == null) {
            throw new WrongException(ErrorConst.WRONG_EXCEPTION.getMessage());
        }
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginMember.getId().toString(), loginMember.getAuthority()));
        cookie.setMaxAge(60 * 10);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setSecure(false);
        response.addCookie(cookie);

        log.info("login member={}", loginMember.toString());
        LoginOutputDto outputDto = new LoginOutputDto(loginMember);
        return outputDto;
    }
}
