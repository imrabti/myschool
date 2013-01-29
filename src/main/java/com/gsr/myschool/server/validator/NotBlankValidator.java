package com.gsr.myschool.server.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotBlankValidator implements ConstraintValidator<NotBlank, Object> {

    public void initialize(NotBlank constraintAnnotation) {
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value instanceof String) {
            String strValue = (String) value;
            return (strValue != null) && (strValue.trim().length() > 0);
        } else {
            return value != null;
        }
    }
}

