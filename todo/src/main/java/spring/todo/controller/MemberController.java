package spring.todo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import spring.todo.domain.Member;
import spring.todo.exception.DuplicateException;
import spring.todo.exception.ErrorConst;
import spring.todo.exception.ErrorResult;
import spring.todo.exception.WrongAccessException;
import spring.todo.repository.member.LoginOutputDto;
import spring.todo.repository.member.MemberDto;
import spring.todo.service.MemberService;

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
    public Long join(MemberDto memberDto) {
        log.info("email={}", memberDto.getEmail());
        log.info("password={}", memberDto.getPassword());
        log.info("nickname={}", memberDto.getNickname());
        Long memberId = memberService.join(memberDto);
        if (memberId == null) {
            throw new DuplicateException(ErrorConst.DUPLICATE_EXCEPTION.getMessage());
        }
        return memberId;
    }

    @PostMapping("/members/{id}")
    public LoginOutputDto memberInfo(@PathVariable String id) {
        Long memberId = Long.parseLong(id);
        Member findMember = memberService.getMemberInfo(memberId);
        if (findMember == null) {
            throw new WrongAccessException(ErrorConst.WRONG_EXCEPTION.getMessage());
        }
        log.info("findMember={}", findMember.toString());
        LoginOutputDto outputDto = new LoginOutputDto(findMember.getEmail(), findMember.getNickname());
        log.info("email = {}, nickname={}", outputDto.getEmail(), outputDto.getNickname());
        return outputDto;
    }
}
