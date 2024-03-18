package spring.todo.domain.todo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
public class Todo {
    @Column(name = "todo_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "list_id")
    private Long listId;

    private String description;

    private boolean isCompleted;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
