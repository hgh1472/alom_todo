package spring.todo.domain.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.todo.domain.todo.domain.TodoList;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {

    public Long save(TodoList todoList);

    public TodoList readTodoListByCode(Long code);

    public Long deleteTodoListByCode(Long code);
}
