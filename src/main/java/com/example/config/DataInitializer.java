package com.example.config;

import com.example.model.User;
import com.example.service.UserService;
import java.time.LocalDate;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final UserService userService;

    @PostConstruct
    public void dataInitialisation() {
        User user;
        user = new User(1L,
                "test@example.com",
                "First name",
                "Last name",
                LocalDate.now().minusYears(10),
                "Address",
                "+380 (50) 422-50-26");
        userService.add(user);

        user = new User(2L,
                "test@example.com",
                "First name",
                "Last name",
                LocalDate.now().minusYears(20),
                "Address",
                "+380 (50) 422-50-26");
        userService.add(user);

        user = new User(3L,
                "test@example.com",
                "First name",
                "Last name",
                LocalDate.now().minusYears(30),
                "Address",
                "+380 (50) 422-50-26");
        userService.add(user);
    }
}
