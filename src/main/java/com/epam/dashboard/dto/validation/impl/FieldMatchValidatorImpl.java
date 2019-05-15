package com.epam.dashboard.dto.validation.impl;

import com.epam.dashboard.dto.validation.FieldMatch;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.BeanUtils;

public class FieldMatchValidatorImpl implements ConstraintValidator<FieldMatch, Object> {

  private String firstFieldName;
  private String secondFieldName;

  @Override
  public void initialize(final FieldMatch constraintAnnotation) {
    firstFieldName = constraintAnnotation.first();
    secondFieldName = constraintAnnotation.second();
  }

  @Override
  public boolean isValid(final Object value, final ConstraintValidatorContext context) {
    try {
      final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
      final Object secondObj = BeanUtils.getProperty(value, secondFieldName);

      return Objects.isNull(firstObj) && Objects.isNull(secondObj) ||
          Objects.equals(firstObj, secondObj);
    } catch (final Exception ignore) {
    }
    return true;
  }
}