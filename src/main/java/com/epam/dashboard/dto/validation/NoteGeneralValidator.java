package com.epam.dashboard.dto.validation;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.epam.dashboard.dto.validation.impl.NoteGeneralValidatorImpl;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

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