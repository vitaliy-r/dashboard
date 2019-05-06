package com.epam.dashboard.dto.validation.impl;

import com.epam.dashboard.dto.UserDto;
import com.epam.dashboard.dto.validation.EqualPasswords;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EqualPasswordsImpl implements ConstraintValidator<EqualPasswords, UserDto> {

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
        return StringUtils.equals(userDto.getPassword(), userDto.getEqualPassword());
    }
}
