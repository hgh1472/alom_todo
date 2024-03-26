package spring.todo.domain.todo.repository;

import lombok.Builder;
import lombok.Data;
import spring.todo.domain.member.domain.Member;
import spring.todo.domain.todo.domain.TodoListInfo;

import java.util.List;

@Data
@Builder
public class CreateRequestDto {
    private List<TodoDto> dtoList;

    private Long writerId;

    public TodoListInfo toEntity(Member writer) {
        return TodoListInfo.builder()
                .todoList(dtoList.stream()
                        .map(todoDto -> todoDto.toEntity())
                        .toList())
                .writer(writer)
                .build();
    }
}
