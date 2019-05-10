package com.epam.dashboard.dto.validation.impl;

import com.epam.dashboard.dto.BoardDto;
import com.epam.dashboard.dto.validation.UniqueTitleValidator;
import com.epam.dashboard.service.BoardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class TitleValidatorImpl implements ConstraintValidator<UniqueTitleValidator, String> {

    @Autowired
    private BoardService boardService;

    private Class<?> dtoClass;

    @Override
    public void initialize(UniqueTitleValidator constraintAnnotation) {
        dtoClass = constraintAnnotation.dtoClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true; // already validated by @NotBlank annotation
        }

        if (Objects.equals(dtoClass, BoardDto.class)) {
            return !boardService.isBoardExistsWithTitle(value);
        }

        return false;
    }

}
