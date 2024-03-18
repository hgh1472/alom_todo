package spring.todo.domain.member.repository;

import lombok.Data;

@Data
public class MemberDto {
    private String email;
    private String password;
    private String nickname;

}
