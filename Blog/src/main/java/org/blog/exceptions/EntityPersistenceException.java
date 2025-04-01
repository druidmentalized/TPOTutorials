package org.blog.exceptions;

public class EntityPersistenceException extends RuntimeException {
    public EntityPersistenceException(String message) {
        super(message);
    }

    public EntityPersistenceException(String message, Exception e) {
    }
}
