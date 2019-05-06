package com.epam.dashboard.dto.validation;

import com.epam.dashboard.dto.validation.impl.UserUniqueFieldImpl;
import com.epam.dashboard.model.enums.UserUniqueField;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserUniqueFieldImpl.class)
public @interface UserUniqueFieldValidator {

    UserUniqueField field();

    String message() default "Passwords should be equal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
