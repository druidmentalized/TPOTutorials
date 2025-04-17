package org.project.tpo07.exceptions;

public class ResultPersistenceException extends RuntimeException {
    public ResultPersistenceException(String message) {
        super(message);
    }

    public ResultPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
