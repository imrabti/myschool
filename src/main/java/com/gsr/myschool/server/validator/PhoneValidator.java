package com.gsr.myschool.server.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {
    private final String PHONE_REGEXP = "(\\+|\\d|-|\\s|\\/|\\(|\\)){5,30}";

    public void initialize(Phone constraintAnnotation) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().equals("")) return true;
        return value.matches(PHONE_REGEXP);
    }
}
