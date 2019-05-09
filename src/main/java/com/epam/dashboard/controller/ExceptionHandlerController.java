package com.epam.dashboard.controller;

import com.epam.dashboard.exception.AbstractServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@ResponseBody
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(AbstractServiceException.class)
    @ResponseStatus(BAD_REQUEST)
    public String handleServiceException(AbstractServiceException e, HandlerMethod hm) {
        log.warn("handleServiceException: service exception in method '{}', message: {}",
                hm.getMethod().getName(), e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public String handleUnexpectedException(Throwable e, HandlerMethod hm) {
        log.warn("handleUnexpectedException: unexpected exception in method '{}', details: {}",
                hm.getMethod().getName(), e.getMessage());
        return e.getMessage();
    }
}
