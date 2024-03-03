package spring.todo.repository.member;

import lombok.Data;
import spring.todo.domain.Member;

@Data
public class MemberDto {
    private String email;
    private String password;
    private String nickname;

}
