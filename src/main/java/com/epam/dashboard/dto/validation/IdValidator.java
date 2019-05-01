package com.epam.dashboard.dto.validation;

import com.epam.dashboard.dto.validation.impl.IdValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdValidatorImpl.class)
public @interface IdValidator {

    Class<?> dtoClass();

    String message() default "Object is not found by id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
