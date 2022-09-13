package com.example.dto.request;

import com.example.lib.AgeValidate;
import com.example.lib.OnCreateOrFullUpdate;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotNull(message = "shouldn't be empty", groups = OnCreateOrFullUpdate.class)
    @Pattern(regexp = "^(.+)@(.+)$", message = "should be email address")
    public String email;
    @NotBlank(message = "shouldn't be blank", groups = OnCreateOrFullUpdate.class)
    public String firstName;
    @NotBlank(message = "shouldn't be blank", groups = OnCreateOrFullUpdate.class)
    public String lastName;
    @NotNull(message = "shouldn't be empty", groups = OnCreateOrFullUpdate.class)
    @AgeValidate
    public LocalDate birthday;
    public String address;
    public String phone;
}
