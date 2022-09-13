package com.example.db;

import com.example.model.User;
import java.util.HashMap;
import java.util.Map;

public class UserStorage {
    public static final Map<Long, User> users = new HashMap<>();
}
