package io.cosgriff.todo.services;

import io.cosgriff.todo.models.Todo;
import io.cosgriff.todo.repositories.TodoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;
    private TodoService todoService;
    private Todo todo1;
    private Todo todo2;
    List<Todo> todoList;

    @BeforeEach
    public void setUp() {
        this.todoService = new TodoService(this.todoRepository);
        todoList = new ArrayList<>();
        todo1 = new Todo(1L, "bread", "20");
        todo2 = new Todo(2L, "jam", "200");
        todoList.add(todo1);
        todoList.add(todo2);
    }

    @AfterEach
    public void tearDown() {
        todo1 = todo2 = null;
        todoList = null;
    }

    @Test
    void givenTodoToAddShouldReturnAddedTodo() {
        Mockito.when(todoRepository.save(Mockito.any())).thenReturn(todo1);
        todoService.save(todo1);
        Mockito.verify(todoRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void givenGetAllUsersShouldReturnListOfAllUsers() {
        todoRepository.save(todo1);

        Mockito.when(todoRepository.findAll()).thenReturn(todoList);

        List<Todo> todoList1 = todoService.findAll();
        Assertions.assertEquals(todoList1, todoList);

        Mockito.verify(todoRepository, Mockito.times(1)).save(todo1);
        Mockito.verify(todoRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void givenIdThenShouldReturnTodoOfThatId() {
        Mockito.when(todoRepository.findById(1L)).thenReturn(Optional.ofNullable(todo1));
        Assertions.assertEquals(todoService.findById(todo1.getId()), todo1);
    }

    @Test
    public void givenIdTODeleteThenShouldDeleteTheProduct() {
        todoService.delete(todo1.getId());
        Mockito.verify(todoRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }
}
