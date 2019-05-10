package com.epam.dashboard.controller;

import com.epam.dashboard.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

@Slf4j
@ControllerAdvice
public class ErrorHandlingController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = resolveAllErrors(e.getBindingResult().getAllErrors());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException e, HandlerMethod hm) {
        log.warn("handleValidationException: validation exception in method '{}', message: {}",
                hm.getMethod().getName(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleUnexpectedException(Throwable e, HandlerMethod hm) {
        log.warn("handleUnexpectedException: unexpected exception in method '{}', details: {}",
                hm.getMethod().getName(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    private String resolveAllErrors(List<ObjectError> errors) {
        return errors.stream()
                .map(error -> format("'%s': %s", error.getObjectName(), error.getDefaultMessage()))
                .collect(joining("\n"));
    }

}
