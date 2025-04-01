package org.books.exceptions;

public class EntityDeletionNotAllowedException extends RuntimeException {
    public EntityDeletionNotAllowedException(String message) {
        super(message);
    }
}
