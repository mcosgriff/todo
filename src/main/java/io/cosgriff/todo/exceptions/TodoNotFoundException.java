package io.cosgriff.todo.exceptions;

public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(Long id) {
        super("Could not find todo " + id);
    }
}
