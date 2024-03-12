package spring.todo.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.todo.domain.Member;
import spring.todo.exception.EmptyException;
import spring.todo.exception.ErrorConst;
import spring.todo.exception.ErrorResult;
import spring.todo.exception.WrongException;
import spring.todo.repository.member.LoginDto;
import spring.todo.security.JwtTokenDto;
import spring.todo.security.JwtTokenProvider;
import spring.todo.service.LoginService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

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
    public JwtTokenDto login(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult, HttpServletResponse response) {

        log.info("[login]");
        if (bindingResult.hasErrors()) {
            throw new EmptyException(ErrorConst.EMPTY_EXCEPTION.getMessage());
        }

        Member loginMember = loginService.login(loginDto.getEmail(), loginDto.getPassword());
        if (loginMember == null) {
            throw new WrongException(ErrorConst.WRONG_EXCEPTION.getMessage());
        }
        log.info("[loginV2] loginMember = {}", loginMember.toString());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        log.info("authenticationToken = {}, {}",authenticationToken.getName(), authenticationToken.getCredentials());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.info("authorities = {}", authentication.getAuthorities());
        JwtTokenDto jwtTokenDto = jwtTokenProvider.generateToken(authentication);
        log.info("jwtTokenDto = {}", jwtTokenDto.getAccessToken());

        return jwtTokenDto;


    }
}
