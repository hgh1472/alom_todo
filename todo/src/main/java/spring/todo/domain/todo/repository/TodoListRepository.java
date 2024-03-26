package spring.todo.domain.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.todo.domain.todo.domain.TodoListInfo;

public interface TodoListRepository extends JpaRepository<TodoListInfo, Long> {

    public TodoListInfo save(TodoListInfo todoListInfo);

    public TodoListInfo findTodoListInfoByCode(Long code);

    public Long removeTodoListInfoByCode(Long code);
}
