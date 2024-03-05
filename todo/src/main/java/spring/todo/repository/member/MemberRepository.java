package spring.todo.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.todo.domain.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Member save(Member member);

    public List<Member> findByEmail(String email);
}
