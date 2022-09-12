package com.example.controller;

import com.example.dto.UserRequestDto;
import com.example.dto.UserResponseDto;
import com.example.dto.mapper.RequestDtoMapper;
import com.example.dto.mapper.ResponseDtoMapper;
import com.example.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final RequestDtoMapper<UserRequestDto, User> userRequestDtoMapper;
    private final ResponseDtoMapper<UserResponseDto, User> userResponseDtoMapper;

    @GetMapping
    public String getAll() {
        return "Hello mate";
    }
}
