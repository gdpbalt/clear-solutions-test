package com.example.lib;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

public class AgeValidator
        implements ConstraintValidator<AgeValidate, LocalDate> {
    @Value("${local.variable.age.restriction:18}")
    private int ageRestriction;

    @Override
    public boolean isValid(LocalDate birthday, ConstraintValidatorContext context) {
        if (birthday == null) {
            return true;
        }
        long age = ChronoUnit.YEARS.between(birthday, LocalDate.now());
        if (age < ageRestriction) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            String.format("Age less than age limit (%s years).",
                                    ageRestriction))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
