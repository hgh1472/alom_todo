package spring.todo.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.todo.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Member save(Member member);

    public Optional<Member> findByEmail(String email);

    public Optional<Member> findById(Long id);
}
