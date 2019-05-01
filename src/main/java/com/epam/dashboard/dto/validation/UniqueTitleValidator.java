package com.epam.dashboard.dto.validation;

import com.epam.dashboard.dto.validation.impl.TitleValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

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