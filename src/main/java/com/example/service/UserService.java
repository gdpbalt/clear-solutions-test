package com.example.service;

import com.example.model.User;
import java.time.LocalDate;
import java.util.List;

public interface UserService {

    List<User> findByBirthday(LocalDate fromDate, LocalDate toDate);

    User findById(Long userId);

    User add(User user);

    User save(User user);

    void delete(User user);
}
