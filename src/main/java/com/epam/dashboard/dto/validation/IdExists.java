package com.epam.dashboard.dto.validation;

import com.epam.dashboard.dto.BoardDto;
import com.epam.dashboard.dto.UserDto;
import com.epam.dashboard.dto.validation.IdExists.IdValidatorImpl;
import com.epam.dashboard.exception.InvalidIdException;
import com.epam.dashboard.exception.RecordIsNotFoundException;
import com.epam.dashboard.service.BoardService;
import com.epam.dashboard.service.UserService;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.springframework.beans.factory.annotation.Autowired;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdValidatorImpl.class)
public @interface IdExists {

  Class<?> dtoClass();

  String message() default "{com.epam.dashboard.dto.validation.IdExists.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  class IdValidatorImpl implements ConstraintValidator<IdExists, String> {

    @Autowired
    private BoardService boardService;
    @Autowired
    private UserService userService;

    private Class<?> dtoClass;

    @Override
    public void initialize(IdExists constraintAnnotation) {
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

}
