package spring.todo.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.todo.domain.member.domain.Member;
import spring.todo.domain.member.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public Member login(String loginEmail, String password) {
        Optional<Member> findMembers = memberRepository.findByEmail(loginEmail);
        return findMembers.filter(member -> passwordEncoder.matches(password, member.getPassword()))
                .orElse(null);
    }
}
