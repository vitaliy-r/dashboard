package com.epam.dashboard.exception;

public class InvalidIdException extends ValidationException {

    private static final String DEFAULT_MESSAGE = "Id cannot be null or empty";

    public InvalidIdException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidIdException(String errorMsg) {
        super(errorMsg);
    }

    public InvalidIdException(Exception e) {
        super(DEFAULT_MESSAGE, e);
    }

    public InvalidIdException(String errorMsg, Exception e) {
        super(errorMsg, e);
    }

}
