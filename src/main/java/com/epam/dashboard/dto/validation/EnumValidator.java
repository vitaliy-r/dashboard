package com.epam.dashboard.dto.validation;

import com.epam.dashboard.dto.validation.impl.EnumValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

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