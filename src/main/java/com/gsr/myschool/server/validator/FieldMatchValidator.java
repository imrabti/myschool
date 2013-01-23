package com.gsr.myschool.server.validator;

import com.google.common.base.Objects;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;

    public void initialize(FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    public boolean isValid(final Object value, ConstraintValidatorContext context) {
        try {
            final String firstObj = Objects.firstNonNull(BeanUtils.getProperty(value, firstFieldName), "");
            final String secondObj = Objects.firstNonNull(BeanUtils.getProperty(value, secondFieldName), "");
            return firstObj.equals(secondObj);
        } catch (final Exception ignore) {
            ignore.printStackTrace();
        }
        return true;
    }
}
