package org.books.exceptions;

public class EntityPersistenceException extends RuntimeException {
    public EntityPersistenceException(String message) {
        super(message);
    }

    public EntityPersistenceException(String message, Exception e) {
    }
}
