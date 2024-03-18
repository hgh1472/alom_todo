package spring.todo.domain.member.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.todo.domain.member.domain.Member;
import spring.todo.domain.member.repository.MemberDto;
import spring.todo.domain.member.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;


    public Long join(MemberDto memberDto) {

        Optional<Member> findMember = memberRepository.findByEmail(memberDto.getEmail());
        if (!findMember.isEmpty()) {
            log.error("이미 존재하는 이메일={}", memberDto.getEmail());
            return null;
        }
        Member member = new Member(memberDto.getEmail(), passwordEncoder.encode(memberDto.getPassword()), memberDto.getNickname());
        memberRepository.save(member);
        log.info("joined member={}", member);
        return member.getId();
    }

    public Member getMemberInfo(Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        return findMember.filter(member -> member.getId().equals(memberId))
                .orElse(null);
    }
}
