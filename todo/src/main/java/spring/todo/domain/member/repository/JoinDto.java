package spring.todo.domain.member.repository;

import lombok.Data;

@Data
public class JoinDto {
    private String email;
    private String password;
    private String nickname;

}
