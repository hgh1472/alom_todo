package spring.todo.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import spring.todo.domain.Member;
import spring.todo.exception.DuplicateException;
import spring.todo.exception.ErrorResult;
import spring.todo.repository.member.MemberDto;
import spring.todo.repository.member.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateException.class)
    public ErrorResult duplicateExceptionHandler(DuplicateException e) {
        log.error("[duplicated email]", e);
        return new ErrorResult("DUPLICATE", e.getMessage());
    }

    public Long join(MemberDto memberDto) {

        List<Member> findMember = memberRepository.findByEmail(memberDto.getEmail());
        if (!findMember.isEmpty()) {
            throw new DuplicateException("이미 존재하는 이메일입니다.");
        }
        Member member = new Member();
        member.setEmail(memberDto.getEmail());
        member.setPassword(memberDto.getPassword());
        member.setNickname(memberDto.getNickname());
        memberRepository.save(member);
        return member.getId();
    }
}
