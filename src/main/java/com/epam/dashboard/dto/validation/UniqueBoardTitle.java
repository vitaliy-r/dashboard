package com.epam.dashboard.dto.validation;

import com.epam.dashboard.dto.validation.UniqueBoardTitle.UniqueBoardTitleImpl;
import com.epam.dashboard.service.BoardService;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueBoardTitleImpl.class)
public @interface UniqueBoardTitle {

  String message() default "{com.epam.dashboard.dto.validation.UniqueBoardTitle.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  class UniqueBoardTitleImpl implements ConstraintValidator<UniqueBoardTitle, String> {

    @Autowired
    private BoardService boardService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
      if (StringUtils.isBlank(value)) {
        return true; // already validated by @NotBlank annotation
      }

      return !boardService.isBoardExistsWithTitle(value);
    }

  }

}