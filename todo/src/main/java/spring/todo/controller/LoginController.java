package spring.todo.controller;

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
import spring.todo.service.LoginService;
import spring.todo.service.MemberService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

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
    public Member login(@Valid @ModelAttribute LoginDto loginDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new EmptyException(ErrorConst.EMPTY_EXCEPTION.getMessage());
        }

        Member loginMember = loginService.login(loginDto.getEmail(), loginDto.getPassword());
        if (loginMember == null) {
            throw new WrongException(ErrorConst.WRONG_EXCEPTION.getMessage());
        }
        return loginMember;
    }
}
