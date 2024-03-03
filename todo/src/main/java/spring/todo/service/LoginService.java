package spring.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.todo.domain.Member;
import spring.todo.repository.member.MemberRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginEmail, String password) {
        return memberRepository.findByEmail(loginEmail)
                .stream()
                .filter(member -> member.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
