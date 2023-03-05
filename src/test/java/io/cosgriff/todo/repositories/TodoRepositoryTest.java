package io.cosgriff.todo.repositories;

import io.cosgriff.todo.models.Todo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TodoRepositoryTest {
    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
        todoRepository.deleteAll();
        todoRepository.flush();
    }

    @Test
    public void givenTodoToAddShouldReturnAddedTodo() {
        Todo todo = new Todo(5L, "Important", "This needs to be performed.");

        Todo fetchedTodo = todoRepository.findById(todo.getId()).get();
        Assertions.assertEquals(5L, fetchedTodo.getId());
    }

    @Test
    public void givenGetAllTodoShouldReturnListOfAllTodos() {
        todoRepository.save(new Todo(1L, "todo1", "Shopping list"));
        todoRepository.save(new Todo(2L, "todo2", "Grocery list"));
        List<Todo> productList = (List<Todo>) todoRepository.findAll();

        Assertions.assertEquals("todo1", productList.get(0).getTitle());
        Assertions.assertEquals("todo2", productList.get(1).getTitle());

        Assertions.assertEquals("Shopping list", productList.get(0).getDescription());
        Assertions.assertEquals("Grocery list", productList.get(1).getDescription());
    }

    @Test
    public void givenIdThenShouldReturnTodoOfThatId() {
        Todo todo1 = new Todo(1L, "rei", "Camping gear");
        Todo todo2 = todoRepository.save(todo1);

        Optional<Todo> optional = todoRepository.findById(todo2.getId());

        Assertions.assertEquals(todo2.getId(), optional.get().getId());
        Assertions.assertEquals(todo2.getTitle(), optional.get().getTitle());
    }

    @Test
    public void givenIdTODeleteThenShouldDeleteTheTodo() {
        Todo todo = new Todo(4L, "Sprouts", "Stuff for chili");
        todoRepository.save(todo);
        todoRepository.deleteById(todo.getId());

        Optional<Todo> optional = todoRepository.findById(todo.getId());
        Assertions.assertEquals(Optional.empty(), optional);
    }
}
