package spring.todo.domain.todo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.todo.domain.todo.domain.TodoListInfo;
import spring.todo.domain.todo.repository.TodoListRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoService {
    private final TodoListRepository todoListRepository;

    public Long registerTodoList(TodoListInfo todoListInfo) {
        todoListInfo.getTodoList()
                .stream()
                .forEach(todo -> todo.setTodoListInfo(todoListInfo));
        Long code = todoListRepository.save(todoListInfo).getCode();
        return code;
    }

    public TodoListInfo readTodoListInfo(Long code) {
        TodoListInfo findTodoListInfo = todoListRepository.findTodoListInfoByCode(code);
        return findTodoListInfo;
    }

    @Transactional
    public Long deleteTodoListInfo(Long code) {
        return todoListRepository.removeTodoListInfoByCode(code);
    }
}
