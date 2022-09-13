package com.example.lib;

import com.example.dto.UserControllerFindByBirthdayParameters;
import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DatesValueValidator
        implements ConstraintValidator<DatesValueValidate, UserControllerFindByBirthdayParameters> {

    @Override
    public boolean isValid(UserControllerFindByBirthdayParameters value,
                           ConstraintValidatorContext context) {
        LocalDate fromDate = value.from();
        LocalDate toDate = value.to();
        if (fromDate == null || toDate == null) {
            return true;
        }
        if (fromDate.isAfter(toDate)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            String.format("From (%s) is after to (%s), which is invalid.",
                                    fromDate, toDate))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
