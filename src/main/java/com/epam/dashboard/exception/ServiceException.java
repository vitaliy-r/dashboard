package com.epam.dashboard.exception;

import com.epam.dashboard.model.enums.ErrorCode;
import com.epam.dashboard.model.enums.ErrorType;

public abstract class ServiceException extends RuntimeException {

    ServiceException(String errorMsg) {
        super(errorMsg);
    }

    ServiceException(String errorMsg, Exception e) {
        super(errorMsg, e);
    }

    public ErrorCode getErrorCode() {
        return ErrorCode.APPLICATION_ERROR_CODE;
    }

    public ErrorType getErrorType() {
        return ErrorType.FATAL_ERROR_TYPE;
    }

}
