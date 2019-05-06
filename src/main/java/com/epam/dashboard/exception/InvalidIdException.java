package com.epam.dashboard.exception;

public class InvalidIdException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Id cannot be null or empty";

    public InvalidIdException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidIdException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    public InvalidIdException(String message) {
        super(message);
    }

    public InvalidIdException(String message, Throwable cause) {
        super(message, cause);
    }

}
