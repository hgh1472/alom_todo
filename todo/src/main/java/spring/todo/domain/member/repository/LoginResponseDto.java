package spring.todo.domain.member.repository;

import lombok.Builder;
import lombok.Data;
import spring.todo.domain.member.domain.Member;

@Data
@Builder
public class LoginResponseDto {
    private String email;
    private String nickname;

    public static LoginResponseDto of(Member member) {
        return LoginResponseDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }
}
