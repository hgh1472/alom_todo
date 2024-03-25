package spring.todo.domain.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import spring.todo.domain.member.domain.Member;
import spring.todo.exception.DuplicateException;
import spring.todo.exception.ErrorConst;
import spring.todo.exception.ErrorResult;
import spring.todo.exception.WrongAccessException;
import spring.todo.domain.member.repository.LoginResponseDto;
import spring.todo.domain.member.repository.JoinDto;
import spring.todo.domain.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateException.class)
    public ErrorResult duplicateExceptionHandler(DuplicateException e) {
        log.error("[duplicated email]", e);
        return new ErrorResult(ErrorConst.DUPLICATE_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongAccessException.class)
    public ErrorResult wrongAccessExceptionHandler(WrongAccessException e) {
        log.error("[wrong access]", e);
        return new ErrorResult(ErrorConst.WRONG_ACCESS_EXCEPTION);
    }

    @PostMapping("/join")
    public Long join(@RequestBody JoinDto joinDto) {
        log.info("[MemberController.join] JoinDto : {}", joinDto);
        Long memberId = memberService.join(joinDto);
        if (memberId == null) {
            throw new DuplicateException(ErrorConst.DUPLICATE_EXCEPTION.getMessage());
        }
        return memberId;
    }

    @PostMapping("/members/{id}")
    public LoginResponseDto memberInfo(@PathVariable String id) {
        log.info("[MemberController.memberInfo] id={}", id);
        Long memberId = Long.parseLong(id);
        Member findMember = memberService.getMemberInfo(memberId);
        if (findMember == null) {
            throw new WrongAccessException(ErrorConst.WRONG_EXCEPTION.getMessage());
        }
        LoginResponseDto loginResponseDto = LoginResponseDto.of(findMember);
        log.info("[MemberController.memberInfo] LoginResponseDto={}", loginResponseDto);
        return loginResponseDto;
    }
}
