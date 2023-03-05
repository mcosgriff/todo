package io.cosgriff.todo.services;

import io.cosgriff.todo.exceptions.TodoNotFoundException;
import io.cosgriff.todo.models.Todo;
import io.cosgriff.todo.repositories.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(final TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> findAll() {
        log.info("Find all todo items");
        return this.todoRepository.findAll();
    }

    public Todo save(final Todo todo) {
        log.info("Save todo={}", todo);
        return this.todoRepository.save(todo);
    }

    public Todo findById(final Long id) {
        log.info("Find todo by id={}", id);
        return this.todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
    }

    public void delete(final Long id) {
        log.info("Delete todo with id={}", id);
        this.todoRepository.deleteById(id);
    }

    public void delete(final Todo todo) {
        log.info("Delete todo={}", todo);
        this.todoRepository.delete(todo);
    }

    public Todo updateOrCreate(final Todo todo, final Long id) {
        return this.todoRepository.findById(id).map(
                p -> {
                    // Update
                    log.info("Updating todo");
                    p.setTitle(todo.getTitle());
                    p.setDescription(todo.getDescription());
                    return this.todoRepository.save(todo);
                }
        ).orElseGet(() -> {
            // Create
            log.info("Creating todo instance");
            todo.setId(id);
            return this.todoRepository.save(todo);
        });
    }

    public Todo getOrCreate(final Todo todo, final Long id) {
        return this.todoRepository.findById(id).orElseGet(() -> this.todoRepository.save(todo));
    }
}
