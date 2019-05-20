package com.epam.dashboard.dto.validation;

import com.epam.dashboard.dto.validation.Enum.EnumValidatorImpl;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidatorImpl.class)
public @interface Enum {

  Class<? extends java.lang.Enum<?>> enumClass();

  String message() default "{com.epam.dashboard.dto.validation.Enum.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  class EnumValidatorImpl implements ConstraintValidator<Enum, String> {

    private List<String> valueList = null;

    @SuppressWarnings("rawtypes")
    @Override
    public void initialize(Enum constraintAnnotation) {
      valueList = new ArrayList<>();

      Class<? extends java.lang.Enum<?>> enumClass = constraintAnnotation.enumClass();
      java.lang.Enum[] enumValues = enumClass.getEnumConstants();

      for (java.lang.Enum value : enumValues) {
        valueList.add(value.toString().toUpperCase());
      }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
      if (Objects.isNull(value)) {
        return true;
      }
      return valueList.contains(value.toUpperCase());
    }

  }

}