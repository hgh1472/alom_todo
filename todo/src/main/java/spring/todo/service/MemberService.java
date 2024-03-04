package spring.todo.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import spring.todo.domain.Member;
import spring.todo.exception.DuplicateException;
import spring.todo.exception.ErrorConst;
import spring.todo.exception.ErrorResult;
import spring.todo.repository.member.MemberDto;
import spring.todo.repository.member.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateException.class)
    public ErrorResult duplicateExceptionHandler(DuplicateException e) {
        log.error("[duplicated email]", e);
        return new ErrorResult(ErrorConst.DUPLICATE_EXCEPTION);
    }

    public Long join(MemberDto memberDto) {

        List<Member> findMember = memberRepository.findByEmail(memberDto.getEmail());
        if (!findMember.isEmpty()) {
            throw new DuplicateException(ErrorConst.DUPLICATE_EXCEPTION.getMessage());
        }
        Member member = new Member();
        member.setEmail(memberDto.getEmail());
        member.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        member.setNickname(memberDto.getNickname());
        memberRepository.save(member);
        log.info("joined member={}", member);
        return member.getId();
    }
}
