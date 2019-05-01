package com.epam.dashboard.dto.validation.impl;

import com.epam.dashboard.dto.BoardDto;
import com.epam.dashboard.dto.validation.IdValidator;
import com.epam.dashboard.dto.validation.UniqueTitleValidator;
import com.epam.dashboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class TitleValidatorImpl implements ConstraintValidator<UniqueTitleValidator, String> {

    @Autowired
    private BoardService boardService;

    private Class<?> dtoClass;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return true;
        }

        if (Objects.equals(dtoClass, BoardDto.class)) {
            return boardService.isBoardExistsWithTitle(value);
        }
        return false;
    }

    @Override
    public void initialize(UniqueTitleValidator constraintAnnotation) {
        dtoClass = constraintAnnotation.dtoClass();
    }

}
