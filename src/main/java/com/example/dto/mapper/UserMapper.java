package com.example.dto.mapper;

import com.example.dto.request.UserRequestDto;
import com.example.dto.response.UserResponseDto;
import com.example.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper implements RequestDtoMapper<UserRequestDto, User>,
        ResponseDtoMapper<UserResponseDto, User> {

    @SuppressWarnings("DuplicatedCode")
    @Override
    public User toModel(UserRequestDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setBirthday(dto.getBirthday());
        user.setAddress(dto.getAddress() == null ? "" : dto.getAddress());
        user.setPhone(dto.getPhone() == null ? "" : dto.getPhone());
        return user;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setBirthday(user.getBirthday());
        dto.setAddress(user.getAddress());
        dto.setPhone(user.getPhone());
        return dto;
    }
}
