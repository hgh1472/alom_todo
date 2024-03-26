package spring.todo.domain.todo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.todo.domain.member.domain.Member;
import spring.todo.domain.member.service.MemberService;
import spring.todo.domain.todo.domain.TodoListInfo;
import spring.todo.domain.todo.repository.CreateRequestDto;
import spring.todo.domain.todo.repository.ReadTodoListDto;
import spring.todo.domain.todo.service.TodoService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TodoListController {

    private final MemberService memberService;
    private final TodoService todoService;

    @PostMapping("/todolist/create")
    public Long createTodoList(@RequestBody CreateRequestDto createRequestDto) {
        log.info("[createTodoList] writerId = {}", createRequestDto.getWriterId());

        Member writer = memberService.getMemberInfo(createRequestDto.getWriterId());
        TodoListInfo todoListInfo = createRequestDto.toEntity(writer);
        return todoService.registerTodoList(todoListInfo);
    }

    @PostMapping("/todolist/{code}")
    public ReadTodoListDto readTodoListInfo(@PathVariable Long code) {
        TodoListInfo todoListInfo = todoService.readTodoListInfo(code);
        ReadTodoListDto readTodoListDto = ReadTodoListDto.of(todoListInfo);
        log.info("[TodoListController.readTodoListInfo] ReadTodoListDto={}", readTodoListDto);
        return readTodoListDto;
    }

    @PostMapping("/todolist/{code}/delete")
    public Long deleteTodoListInfo(@PathVariable Long code) {
        return todoService.deleteTodoListInfo(code);
    }
}
