package com.example.lib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DatesValueValidator.class)
public @interface DatesValueValidate {
    String message() default "`from` should be more recent then `to` or equal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
