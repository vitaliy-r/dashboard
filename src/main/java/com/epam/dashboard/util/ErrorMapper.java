package com.epam.dashboard.util;

import com.epam.dashboard.exception.ServiceException;
import com.epam.dashboard.exception.ValidationException;
import com.epam.dashboard.model.Error;
import com.epam.dashboard.model.enums.ErrorCode;
import com.epam.dashboard.model.enums.ErrorType;
import com.mongodb.MongoException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.stream.Collectors;

public class ErrorMapper {

    private ErrorMapper() {
    }

    public static List<Error> mapException(MethodArgumentNotValidException e) {
        return e.getBindingResult().getAllErrors().stream()
                .map(error -> new Error(ErrorType.PROCESSING_ERROR_TYPE.name(), ErrorCode.VALIDATION_ERROR_CODE.name(),
                        String.format("'%s': %s", error.getObjectName(), error.getDefaultMessage())))
                .collect(Collectors.toList());
    }

    public static Error mapException(ValidationException e) {
        return new Error(e.getErrorType().name(), e.getErrorCode().name(), e.getMessage());
    }

    public static Error mapException(ServiceException e) {
        return new Error(e.getErrorType().name(), e.getErrorCode().name(), e.getMessage());
    }

    public static Error mapException(MongoException e) {
        return new Error(ErrorType.PROCESSING_ERROR_TYPE.name(), ErrorCode.MONGO_ERROR_CODE.name(), e.getMessage());
    }

    public static Error mapException(Throwable e) {
        return new Error(ErrorType.FATAL_ERROR_TYPE.name(), ErrorCode.APPLICATION_ERROR_CODE.name(), e.getMessage());
    }

}