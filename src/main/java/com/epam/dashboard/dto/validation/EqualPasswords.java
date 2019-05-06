package com.epam.dashboard.dto.validation;

import com.epam.dashboard.dto.validation.impl.EqualPasswordsImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EqualPasswordsImpl.class)
public @interface EqualPasswords {

    String message() default "Passwords should be equal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
