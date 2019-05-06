package com.epam.dashboard.dto.validation.impl;

import com.epam.dashboard.dto.validation.UserUniqueFieldValidator;
import com.epam.dashboard.model.enums.UserUniqueField;
import com.epam.dashboard.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserUniqueFieldImpl implements ConstraintValidator<UserUniqueFieldValidator, String> {

    @Autowired
    private UserService userService;

    private UserUniqueField fieldName;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true; // already validated by @NotBlank annotation
        }

        switch (fieldName) {
            case EMAIL:
                return userService.isUserExistsWithEmail(value);
            case USERNAME:
                return userService.isUserExistsWithUsername(value);
            default:
                return false;
        }
    }

    @Override
    public void initialize(UserUniqueFieldValidator constraintAnnotation) {
        fieldName = constraintAnnotation.field();
    }

}