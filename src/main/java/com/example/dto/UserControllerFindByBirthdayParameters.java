package com.example.dto;

import com.example.lib.DatesValueValidate;
import com.example.util.DateTimePatternUtil;
import java.time.LocalDate;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

@DatesValueValidate
public record UserControllerFindByBirthdayParameters(
        @Past
        @RequestParam(required = false)
        @DateTimeFormat(pattern = DateTimePatternUtil.DATE_PATTERN)
        LocalDate from,
        @PastOrPresent
        @RequestParam(required = false)
        @DateTimeFormat(pattern = DateTimePatternUtil.DATE_PATTERN)
        LocalDate to) {
}
