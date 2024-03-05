package spring.todo.repository.member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginOutputDto {
    private String email;
    private String nickname;
}
