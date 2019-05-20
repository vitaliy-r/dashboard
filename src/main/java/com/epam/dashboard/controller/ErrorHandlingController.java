package com.epam.dashboard.controller;

import static com.epam.dashboard.util.ErrorMapper.mapException;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.epam.dashboard.exception.ServiceException;
import com.epam.dashboard.exception.ValidationException;
import com.epam.dashboard.model.Error;
import com.mongodb.MongoException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;

@Slf4j
@ResponseBody
@ControllerAdvice
public class ErrorHandlingController {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public List<Error> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    log.warn("handleMethodArgumentNotValidException: validation exception message: {}",
        e.getMessage());
    return mapException(e);
  }

  @ExceptionHandler(ValidationException.class)
  @ResponseStatus(BAD_REQUEST)
  public Error handleValidationException(ValidationException e, HandlerMethod hm) {
    log.warn("handleValidationException: validation exception in method '{}', details: {}, {}, {}",
        hm.getMethod().getName(), e.getErrorType(), e.getErrorCode(), e.getMessage());
    return mapException(e);
  }

  @ExceptionHandler(ServiceException.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public Error handleServiceException(ServiceException e, HandlerMethod hm) {
    log.warn("handleServiceException: service exception in method '{}', details: {}, {}, {}",
        hm.getMethod().getName(), e.getErrorType(), e.getErrorCode(), e.getMessage());
    return mapException(e);
  }

  @ExceptionHandler({DataAccessException.class, MongoException.class})
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public Error handleMongoException(MongoException e, HandlerMethod hm) {
    log.warn("handleMongoException: mongo exception in method '{}', message: {}",
        hm.getMethod().getName(), e.getMessage());
    return mapException(e);
  }

  @ExceptionHandler(Throwable.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public Error handleUnexpectedException(Throwable e, HandlerMethod hm) {
    log.warn("handleUnexpectedException: unexpected exception in method '{}', message: {}",
        hm.getMethod().getName(), e.getMessage());
    return mapException(e);
  }

}
