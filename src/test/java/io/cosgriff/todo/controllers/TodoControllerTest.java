package io.cosgriff.todo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cosgriff.todo.models.Todo;
import io.cosgriff.todo.services.TodoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TodoControllerTest {
    @Mock
    private TodoService todoService;
    private Todo todo;
    private List<Todo> TodoList;
    //@InjectMocks
    private TodoController todoController;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        todo = new Todo(1L, "ball", "670");
        todoController = new TodoController(todoService);
        mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();
    }

    @AfterEach
    void tearDown() {
        todo = null;
    }

    @Test
    public void PostMappingOfTodo() throws Exception {
        Mockito.when(todoService.save(Mockito.any(Todo.class))).thenReturn(todo);

        mockMvc.perform(post("/api/v1/todo").
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(todo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("ball"));

        Mockito.verify(todoService, Mockito.times(1)).save(Mockito.any(Todo.class));
    }
}
