package com.example.service;

import com.example.model.User;
import java.time.LocalDate;
import java.util.List;

public interface UserService {

    List<User> findByBirthday(LocalDate fromDate, LocalDate toDate);

    User add(User user);
}
