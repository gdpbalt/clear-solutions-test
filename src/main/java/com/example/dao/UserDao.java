package com.example.dao;

import com.example.model.User;
import java.time.LocalDate;
import java.util.List;

public interface UserDao {
    List<User> getAll();

    List<User> findByBirthdayAfterOrEqualDate(LocalDate date);

    List<User> findByBirthdayBeforeOrEqualDate(LocalDate date);

    List<User> findByBirthdayBetweenDate(LocalDate fromDate, LocalDate toDate);

    User add(User user);
}
