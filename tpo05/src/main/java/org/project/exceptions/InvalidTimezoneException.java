package org.project.exceptions;

public class InvalidTimezoneException extends RuntimeException {
    public InvalidTimezoneException(String message) {
        super(message);
    }
}
