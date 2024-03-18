package spring.todo.domain.member.repository;

import lombok.Data;
import spring.todo.domain.member.domain.Member;

@Data
public class LoginOutputDto {
    private String email;
    private String nickname;

    public LoginOutputDto(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickname();
    }
}
