package com.epam.dashboard.dto.validation;

import com.epam.dashboard.dto.validation.impl.EnumValidatorImpl;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidatorImpl.class)
public @interface EnumValidator {

  Class<? extends Enum<?>> enumClass();

  String message() default "Field value is not valid";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}