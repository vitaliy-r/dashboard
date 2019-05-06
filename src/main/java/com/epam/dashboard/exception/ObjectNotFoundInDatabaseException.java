package com.epam.dashboard.exception;

public class ObjectNotFoundInDatabaseException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Object is not found in database";

    public ObjectNotFoundInDatabaseException() {
        super(DEFAULT_MESSAGE);
    }

    public ObjectNotFoundInDatabaseException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    public ObjectNotFoundInDatabaseException(String message) {
        super(message);
    }

    public ObjectNotFoundInDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

}
