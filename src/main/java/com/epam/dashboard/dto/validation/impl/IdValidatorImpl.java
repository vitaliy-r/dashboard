package com.epam.dashboard.dto.validation.impl;

import com.epam.dashboard.dto.BoardDto;
import com.epam.dashboard.dto.UserDto;
import com.epam.dashboard.dto.validation.IdValidator;
import com.epam.dashboard.exception.InvalidIdException;
import com.epam.dashboard.exception.RecordIsNotFoundException;
import com.epam.dashboard.service.BoardService;
import com.epam.dashboard.service.UserService;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class IdValidatorImpl implements ConstraintValidator<IdValidator, String> {

  @Autowired
  private BoardService boardService;
  @Autowired
  private UserService userService;

  private Class<?> dtoClass;

  @Override
  public void initialize(IdValidator constraintAnnotation) {
    dtoClass = constraintAnnotation.dtoClass();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    try {
      if (Objects.equals(dtoClass, BoardDto.class)) {
        boardService.findById(value);
      } else if (Objects.equals(dtoClass, UserDto.class)) {
        userService.findById(value);
      }
      return true;
    } catch (InvalidIdException e) {
      return true; // already validated by @NotBlank annotation
    } catch (RecordIsNotFoundException e) {
      return false;
    }
  }

}
