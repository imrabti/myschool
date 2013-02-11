package com.gsr.myschool.server.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    private final String PASSWORD_REGEXP = "(.){6,20}";

    public void initialize(Password constraintAnnotation) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().equals("")) return true;
        return value.matches(PASSWORD_REGEXP);
    }
}
