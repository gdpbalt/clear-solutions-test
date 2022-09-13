package com.example.service;

import com.example.dao.UserDao;
import com.example.model.User;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public List<User> findByBirthday(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null && toDate == null) {
            return userDao.getAll();
        }
        if (fromDate != null && toDate != null) {
            return userDao.findByBirthdayBetweenDate(fromDate, toDate);
        }
        if (fromDate != null) {
            return userDao.findByBirthdayAfterOrEqualDate(fromDate);
        }
        return userDao.findByBirthdayBeforeOrEqualDate(toDate);
    }

    @Override
    public User add(User user) {
        return userDao.add(user);
    }
}