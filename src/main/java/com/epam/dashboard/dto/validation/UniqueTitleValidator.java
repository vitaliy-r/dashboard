package com.epam.dashboard.dto.validation;

import com.epam.dashboard.dto.validation.impl.TitleValidatorImpl;
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
@Constraint(validatedBy = TitleValidatorImpl.class)
public @interface UniqueTitleValidator {

  Class<?> dtoClass();

  String message() default "Title is already in use";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}