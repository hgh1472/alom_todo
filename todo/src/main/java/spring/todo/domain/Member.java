package spring.todo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import spring.todo.security.Authority;


@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public Member(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.authority = Authority.MEMBER;
    }
}
