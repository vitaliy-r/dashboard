package com.epam.dashboard.controller;

import com.epam.dashboard.exception.ServiceException;
import com.epam.dashboard.exception.ValidationException;
import com.epam.dashboard.model.Error;
import com.mongodb.MongoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

import java.util.List;

import static com.epam.dashboard.util.ErrorMapper.mapException;

@Slf4j
@ControllerAdvice
public class ErrorHandlingController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Error>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("handleMethodArgumentNotValidException: validation exception message: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapException(e));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Error> handleValidationException(ValidationException e, HandlerMethod hm) {
        log.warn("handleValidationException: validation exception in method '{}', details: {}, {}, {}",
                hm.getMethod().getName(), e.getErrorType(), e.getErrorCode(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapException(e));
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Error> handleServiceException(ServiceException e, HandlerMethod hm) {
        log.warn("handleServiceException: service exception in method '{}', details: {}, {}, {}",
                hm.getMethod().getName(), e.getErrorType(), e.getErrorCode(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapException(e));
    }

    @ExceptionHandler({DataAccessException.class, MongoException.class})
    public ResponseEntity<Error> handleMongoException(MongoException e, HandlerMethod hm) {
        log.warn("handleMongoException: mongo exception in method '{}', message: {}",
                hm.getMethod().getName(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapException(e));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Error> handleUnexpectedException(Throwable e, HandlerMethod hm) {
        log.warn("handleUnexpectedException: unexpected exception in method '{}', message: {}",
                hm.getMethod().getName(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapException(e));
    }

}
