package com.gsr.myschool.server.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NameValidator.class)
public @interface Name {
    String message() default "{com.gsr.myschool.server.validator.Name}";

    String[] params() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
