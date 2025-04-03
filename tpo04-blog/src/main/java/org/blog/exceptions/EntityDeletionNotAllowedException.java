package org.blog.exceptions;

public class EntityDeletionNotAllowedException extends RuntimeException {
    public EntityDeletionNotAllowedException(String message) {
        super(message);
    }
}
