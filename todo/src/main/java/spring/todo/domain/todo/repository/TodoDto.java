package spring.todo.domain.todo.repository;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import spring.todo.domain.todo.domain.Todo;

import java.time.LocalDateTime;

@Data
@Builder
public class TodoDto {

    private String description;

    private boolean isCompleted;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endTime;

    public Todo toEntity() {
        return Todo.builder()
                .description(description)
                .isCompleted(isCompleted)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    public static TodoDto of(Todo todo) {
        return TodoDto.builder()
                .description(todo.getDescription())
                .isCompleted(todo.isCompleted())
                .startTime(todo.getStartTime())
                .endTime(todo.getEndTime())
                .build();
    }
}
