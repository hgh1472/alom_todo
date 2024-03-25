package spring.todo.domain.todo.repository;

import lombok.Builder;
import lombok.Data;
import spring.todo.domain.member.repository.LoginResponseDto;
import spring.todo.domain.todo.domain.TodoListInfo;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ReadTodoListDto {
    private Long code;

    private List<TodoDto> todoDtoList = new ArrayList<>();

    private LoginResponseDto writer;

    public static ReadTodoListDto of(TodoListInfo todoListInfo) {
        return ReadTodoListDto.builder()
                .code(todoListInfo.getCode())
                .todoDtoList(todoListInfo.getTodoList()
                        .stream()
                        .map(todo -> TodoDto.of(todo))
                        .toList())
                .writer(LoginResponseDto.of(todoListInfo.getWriter()))
                .build();
    }
}
