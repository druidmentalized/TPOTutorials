package org.project.tpo07.exceptions;

public class ResultNotFoundException extends RuntimeException {
    public ResultNotFoundException(String message) {
        super(message);
    }

  public ResultNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
