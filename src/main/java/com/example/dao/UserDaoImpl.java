package com.example.dao;

import com.example.db.UserStorage;
import com.example.model.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    @Override
    public List<User> getAll() {
        return new ArrayList<>(UserStorage.users.values());
    }

    @Override
    public List<User> findByBirthdayAfterOrEqualDate(LocalDate date) {
        return UserStorage.users.values().stream()
                .filter(u -> u.getBirthday().isAfter(date) || u.getBirthday().isEqual(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByBirthdayBeforeOrEqualDate(LocalDate date) {
        return UserStorage.users.values().stream()
                .filter(u -> u.getBirthday().isBefore(date) || u.getBirthday().isEqual(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByBirthdayBetweenDate(LocalDate fromDate, LocalDate toDate) {
        Predicate<User> findByBirthdayBetweenDatePredicate = user -> {
            LocalDate birthday = user.getBirthday();
            return (birthday.isEqual(fromDate) || birthday.isAfter(fromDate))
                    && (birthday.isEqual(toDate) || birthday.isBefore(toDate));

        };
        return UserStorage.users.values().stream()
                .filter(findByBirthdayBetweenDatePredicate)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(Long userId) {
        return Optional.ofNullable(UserStorage.users.get(userId));
    }

    @Override
    public User add(User user) {
        user.setId(findNextUserId());
        UserStorage.users.put(user.getId(), user);
        return user;
    }

    @Override
    public User save(User user) {
        UserStorage.users.replace(user.getId(), user);
        return user;
    }

    @Override
    public void delete(User user) {
        UserStorage.users.remove(user.getId());
    }

    private Long findNextUserId() {
        return UserStorage.users.values().stream()
                .map(User::getId)
                .max(Comparator.naturalOrder())
                .orElse(0L) + 1;
    }
}
