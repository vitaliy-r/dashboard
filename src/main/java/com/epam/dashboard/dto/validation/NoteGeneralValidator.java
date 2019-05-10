package com.epam.dashboard.dto.validation;

import com.epam.dashboard.dto.validation.impl.NoteGeneralValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoteGeneralValidatorImpl.class)
@Repeatable(NoteGeneralValidator.List.class)
public @interface NoteGeneralValidator {

    boolean exists();

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target(TYPE_USE)
    @Retention(RUNTIME)
    @Documented
    @interface List {
        NoteGeneralValidator[] value();
    }

}