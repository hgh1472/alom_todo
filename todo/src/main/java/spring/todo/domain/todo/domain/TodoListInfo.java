package spring.todo.domain.todo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import spring.todo.domain.member.domain.Member;

import java.util.List;

@Entity
@Getter
public class TodoListInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    @OneToOne
    private Member writer;

    @OneToMany
    @JoinColumn(name = "list_id")
    private List<Todo> todoList;
}
