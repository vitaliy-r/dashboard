package com.epam.dashboard.exception;

public class ValidationException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Object did not pass validation";

    ValidationException() {
        super(DEFAULT_MESSAGE);
    }

    ValidationException(String errorMsg) {
        super(errorMsg);
    }

    ValidationException(Exception e) {
        super(DEFAULT_MESSAGE, e);
    }

    ValidationException(String errorMsg, Exception e) {
        super(errorMsg, e);
    }

}
