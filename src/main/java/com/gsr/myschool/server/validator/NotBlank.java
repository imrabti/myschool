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
@Constraint(validatedBy = NotBlankValidator.class)
public @interface NotBlank {
    String message() default "{com.gsr.myschool.server.validator.NotBlank}";

    Class<?>[] groups() default {};

    String[] params() default {};

    Class<? extends Payload>[] payload() default {};
}
