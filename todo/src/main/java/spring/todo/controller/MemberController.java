package spring.todo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import spring.todo.domain.Member;
import spring.todo.exception.DuplicateException;
import spring.todo.exception.ErrorConst;
import spring.todo.exception.ErrorResult;
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
}
