package com.epam.dashboard.dto.validation;

import com.epam.dashboard.dto.validation.impl.UserUniqueFieldImpl;
import com.epam.dashboard.model.enums.UserUniqueField;
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
@Constraint(validatedBy = UserUniqueFieldImpl.class)
public @interface UserUniqueFieldValidator {

  UserUniqueField field();

  String message() default "Field value is already in use";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
