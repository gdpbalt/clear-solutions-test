package com.example.controller;

import com.example.dto.UserControllerFindByBirthdayParameters;
import com.example.dto.mapper.RequestDtoMapper;
import com.example.dto.mapper.ResponseDtoMapper;
import com.example.dto.request.UserRequestDto;
import com.example.dto.response.ErrorMessageDto;
import com.example.dto.response.UserResponseDto;
import com.example.model.User;
import com.example.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    private final RequestDtoMapper<UserRequestDto, User> userRequestDtoMapper;
    private final ResponseDtoMapper<UserResponseDto, User> userResponseDtoMapper;

    @PostMapping
    public UserResponseDto createUser(@RequestBody @Valid UserRequestDto requestDto) {
        User user = userRequestDtoMapper.toModel(requestDto);
        return userResponseDtoMapper.toDto(userService.add(user));
    }

    @PostMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable Long id,
                                      @RequestBody @Valid UserRequestDto requestDto) {
        User user = userRequestDtoMapper.toModel(requestDto);
        user.setId(userService.findById(id).getId());
        return userResponseDtoMapper.toDto(userService.save(user));
    }

    @PatchMapping("/{id}")
    public String changeUser(@PathVariable Long id) {
        return "Patch: Hello mate";
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        User user = userService.findById(id);
        userService.delete(user);
    }

    @GetMapping("/by-birthday")
    public List<UserResponseDto> findByBirthday(
            @Valid UserControllerFindByBirthdayParameters parameters) {
        System.out.println(parameters.from());
        System.out.println(parameters.to());
        return userService.findByBirthday(parameters.from(), parameters.to()).stream()
                .map(userResponseDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto handleConstraintViolationException(ConstraintViolationException e) {
        return new ErrorMessageDto(HttpStatus.BAD_REQUEST.value(),
                "not valid due to validation error: " + e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto handleConstraintViolationException(BindException e) {
        return new ErrorMessageDto(HttpStatus.BAD_REQUEST.value(),
                "not valid due to validation error: " + e.getMessage());
    }
}
