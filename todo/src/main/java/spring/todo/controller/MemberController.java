package spring.todo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.todo.domain.Member;
import spring.todo.repository.member.MemberDto;
import spring.todo.service.MemberService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public void join(MemberDto memberDto) {
        log.info("email={}", memberDto.getEmail());
        log.info("password={}", memberDto.getPassword());
        log.info("nickname={}", memberDto.getNickname());
        memberService.join(memberDto);
    }
}
