package com.epam.dashboard.dto.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.epam.dashboard.dto.validation.FieldMatch.FieldMatchValidatorImpl;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.apache.commons.beanutils.BeanUtils;

@Documented
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = FieldMatchValidatorImpl.class)
@Repeatable(FieldMatch.List.class)
public @interface FieldMatch {

  String first();

  String second();

  String message() default "{com.epam.dashboard.dto.validation.FieldMatch.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  @Target({TYPE, ANNOTATION_TYPE})
  @Retention(RUNTIME)
  @Documented
  @interface List {

    FieldMatch[] value();
  }

  class FieldMatchValidatorImpl implements ConstraintValidator<FieldMatch, Object> {

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
        return false;
      }
    }

  }

}