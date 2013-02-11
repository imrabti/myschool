package com.gsr.myschool.server.validator;

import com.gsr.myschool.server.security.SecurityContextProvider;
import com.gsr.myschool.server.util.ApplicationContextProvider;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OldPasswordValidator implements ConstraintValidator<OldPassword, String> {
    private SecurityContextProvider securityContextProvider;

    public void initialize(OldPassword constraintAnnotation) {
        securityContextProvider = ApplicationContextProvider.get().getBean(SecurityContextProvider.class);
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        return securityContextProvider.getCurrentUser().getPassword().equals(value);
    }
}
