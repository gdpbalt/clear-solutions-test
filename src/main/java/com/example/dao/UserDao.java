package com.example.dao;

import com.example.model.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> getAll();

    List<User> findByBirthdayAfterOrEqualDate(LocalDate date);

    List<User> findByBirthdayBeforeOrEqualDate(LocalDate date);

    List<User> findByBirthdayBetweenDate(LocalDate fromDate, LocalDate toDate);

    Optional<User> findById(Long userId);

    User add(User user);

    void delete(User user);

    User save(User user);
}
