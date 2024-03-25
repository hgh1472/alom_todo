package spring.todo.domain.member.repository;

import lombok.Builder;
import lombok.Data;
import spring.todo.domain.member.domain.Member;

@Data
@Builder
public class LoginOutputDto {
    private String email;
    private String nickname;

    public LoginOutputDto(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickname();
    }

    public static LoginOutputDto of(Member member) {
        return LoginOutputDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }
}
