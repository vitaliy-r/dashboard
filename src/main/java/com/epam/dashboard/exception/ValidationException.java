package com.epam.dashboard.exception;

import com.epam.dashboard.model.enums.ErrorCode;
import com.epam.dashboard.model.enums.ErrorType;

public class ValidationException extends ServiceException {

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

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.VALIDATION_ERROR_CODE;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.PROCESSING_ERROR_TYPE;
    }

}
