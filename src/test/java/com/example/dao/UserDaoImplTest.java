package com.example.dao;

import com.example.db.UserStorage;
import com.example.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class UserDaoImplTest {
    private static UserDao userDao;

    public static Map<Long, User> testStorage;

    @BeforeAll
    static void beforeAll() {
        userDao = new UserDaoImpl();
        testStorage = new HashMap<>();
    }

    @BeforeEach
    void setUp() {
        User[] users = new User[]{
                new User(1L, "test1@example.org", "first", "last",
                        LocalDate.of(1980, 10, 1), null, null),
                new User(2L, "test2@example.org", "first", "last",
                        LocalDate.of(1990, 10, 1), null, null),
                new User(3L, "test3@example.org", "first", "last",
                        LocalDate.of(2000, 10, 1), null, null)
        };
        testStorage.put(1L, users[0]);
        testStorage.put(2L, users[1]);
        testStorage.put(3L, users[2]);
        userDao.add(users[0]);
        userDao.add(users[1]);
        userDao.add(users[2]);
    }

    @Test
    void run_add_ok() {
        User user = new User("test4@example.org", "first", "last",
                LocalDate.of(2000, 10, 1), null, null);
        testStorage.put(4L, user);
        userDao.add(user);
        Assertions.assertEquals(testStorage, UserStorage.users);
    }

    @Test
    void run_save_ok() {
        User user = new User(1L, "test_new@example.org", "first", "last",
                LocalDate.of(2000, 10, 1), null, null);
        testStorage.put(1L, user);
        userDao.save(user);
        Assertions.assertEquals(testStorage, UserStorage.users);
    }

    @Test
    void run_getAll_ok() {
        List<User> userList = userDao.getAll();
        Assertions.assertEquals(3, userList.size());
    }

    @Test
    void run_findById_ok() {
        Optional<User> existedUser = userDao.findById(1L);
        Assertions.assertEquals(testStorage.get(1L), existedUser.orElse(null));
    }

    @Test
    void run_delete_ok() {
        User existedUser = userDao.findById(1L).orElse(null);
        userDao.delete(existedUser);
        testStorage.remove(1L);
        Assertions.assertEquals(testStorage, UserStorage.users);
    }

    @Test
    void run_findByBirthdayAfterOrEqualDate_ok() {
        List<User> userList = userDao.findByBirthdayAfterOrEqualDate(
                LocalDate.of(1990, 1, 1));
        Assertions.assertEquals(2, userList.size());
    }

    @Test
    void run_findByBirthdayBeforeOrEqualDate_ok() {
        List<User> userList = userDao.findByBirthdayBeforeOrEqualDate(
                LocalDate.of(1990, 1, 1));
        Assertions.assertEquals(1, userList.size());
    }

    @Test
    void run_findByBirthdayBetweenDate_ok() {
        List<User> userList = userDao.findByBirthdayBetweenDate(
                LocalDate.of(1990, 10, 1),
                LocalDate.of(2000, 10, 1));
        Assertions.assertEquals(2, userList.size());
    }


    @AfterEach
    void tearDown() {
        testStorage.clear();
        UserStorage.users.clear();
    }
}