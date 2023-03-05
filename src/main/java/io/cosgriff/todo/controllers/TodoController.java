package io.cosgriff.todo.controllers;


import io.cosgriff.todo.models.Todo;
import io.cosgriff.todo.services.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TodoController {

    private final TodoService todoService;

    public TodoController(final TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todolist")
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(this.todoService.findAll());
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<Todo> findTodoById(@PathVariable Long id) {
        return ResponseEntity.ok(this.todoService.findById(id));
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id) {
        this.todoService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/todo/{id}")
    public ResponseEntity<Todo> update(@RequestBody Todo todo, @PathVariable Long id) {
        return ResponseEntity.ok(this.todoService.updateOrCreate(todo, id));
    }

    @PostMapping("/todo")
    public ResponseEntity<?> addTodo(@RequestBody Todo todo) {
        return ResponseEntity.ok(this.todoService.save(todo));
    }
}
