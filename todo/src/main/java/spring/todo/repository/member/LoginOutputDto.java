package spring.todo.repository.member;

import lombok.Data;
import spring.todo.domain.Member;

@Data
public class LoginOutputDto {
    private String email;
    private String nickname;

    public LoginOutputDto(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getEmail();
    }
}
