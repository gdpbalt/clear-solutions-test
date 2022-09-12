package com.example.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class User {
    public Long id;
    public String email;
    public String firstName;
    public String lastName;
    public LocalDate birthday;
    public String address;
    public String phone;
}
