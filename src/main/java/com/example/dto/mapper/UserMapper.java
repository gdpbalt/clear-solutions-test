package com.example.dto.mapper;

import com.example.dto.UserRequestDto;
import com.example.dto.UserResponseDto;
import com.example.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper implements RequestDtoMapper<UserRequestDto, User>,
        ResponseDtoMapper<UserResponseDto, User> {

    @Override
    public User toModel(UserRequestDto dto) {
        User user = new User();
        return user;
    }

    @Override
    public UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        return dto;
    }
}
