package com.epam.dashboard.dto.validation;

import com.epam.dashboard.dto.validation.UniqueUserField.UserUniqueFieldImpl;
import com.epam.dashboard.model.enums.UserUniqueField;
import com.epam.dashboard.service.UserService;
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
@Constraint(validatedBy = UserUniqueFieldImpl.class)
public @interface UniqueUserField {

  UserUniqueField field();

  String message() default "{com.epam.dashboard.dto.validation.UniqueUserField.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  class UserUniqueFieldImpl implements ConstraintValidator<UniqueUserField, String> {

    @Autowired
    private UserService userService;

    private UserUniqueField fieldName;

    @Override
    public void initialize(UniqueUserField constraintAnnotation) {
      fieldName = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
      if (StringUtils.isBlank(value)) {
        return true; // already validated by @NotBlank annotation
      }

      switch (fieldName) {
        case EMAIL:
          return !userService.isUserExistsWithEmail(value);
        case USERNAME:
          return !userService.isUserExistsWithUsername(value);
        default:
          return false;
      }
    }

  }

}
