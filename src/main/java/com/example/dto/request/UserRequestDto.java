package com.example.dto.request;

import com.example.lib.AgeValidate;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequestDto {
    @Pattern(regexp = "^(.+)@(.+)$", message = "should be email address")
    public String email;
    @NotBlank(message = "shouldn't be blank")
    public String firstName;
    @NotBlank(message = "shouldn't be blank")
    public String lastName;
    @NotNull(message = "shouldn't be null")
    @Past(message = "data should be in the past")
    @AgeValidate
    public LocalDate birthday;
    public String address;
    public String phone;
}
