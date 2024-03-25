package spring.todo.domain.todo.domain;

import jakarta.persistence.*;
import lombok.*;
import spring.todo.domain.member.domain.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class TodoListInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    @OneToOne
    private Member writer;

    @OneToMany(mappedBy = "todoListInfo", cascade = CascadeType.ALL)
    private List<Todo> todoList = new ArrayList<>();
}
