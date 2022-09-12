package com.example.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class UserRequestDto {
    public String email;
    public String firstName;
    public String lastName;
    public LocalDate birthday;
    public String address;
    public String phone;
}
