package com.epam.dashboard.exception;

public abstract class AbstractServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    AbstractServiceException(String errorMsg) {
        super(errorMsg);
    }

    AbstractServiceException(String errorMsg, Exception e) {
        super(errorMsg, e);
    }

}
