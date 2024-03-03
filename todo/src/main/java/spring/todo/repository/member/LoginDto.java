package spring.todo.repository.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
