package com.epam.dashboard.dto.validation.impl;

import com.epam.dashboard.dto.validation.EnumValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

  private List<String> valueList = null;

  @SuppressWarnings("rawtypes")
  @Override
  public void initialize(EnumValidator constraintAnnotation) {
    valueList = new ArrayList<>();

    Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();
    Enum[] enumValues = enumClass.getEnumConstants();

    for (Enum value : enumValues) {
      valueList.add(value.toString().toUpperCase());
    }
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (Objects.isNull(value)) {
      return true;
    }
    return valueList.contains(value.toUpperCase());
  }

}