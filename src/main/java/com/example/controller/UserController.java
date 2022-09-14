package com.example.controller;

import com.example.dto.UserControllerFindByBirthdayParameters;
import com.example.dto.mapper.RequestDtoMapper;
import com.example.dto.mapper.ResponseDtoMapper;
import com.example.dto.request.UserRequestDto;
import com.example.dto.response.UserResponseDto;
import com.example.lib.OnCreateOrFullUpdate;
import com.example.model.User;
import com.example.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @Validated(OnCreateOrFullUpdate.class)
    public UserResponseDto createUser(@RequestBody @Valid UserRequestDto requestDto) {
        User user = userRequestDtoMapper.toModel(requestDto);
        return userResponseDtoMapper.toDto(userService.add(user));
    }

    @PatchMapping("/{id}")
    public UserResponseDto patchUser(@PathVariable Long id,
                                     @RequestBody @Valid UserRequestDto requestDto) {
        User user = userService.findById(id);
        user = userRequestDtoMapper.toModel(user, requestDto);
        return userResponseDtoMapper.toDto(userService.save(user));
    }

    @PutMapping("/{id}")
    @Validated(OnCreateOrFullUpdate.class)
    public UserResponseDto updateUser(@PathVariable Long id,
                                      @RequestBody @Valid UserRequestDto requestDto) {
        User user = userRequestDtoMapper.toModel(requestDto);
        user.setId(userService.findById(id).getId());
        return userResponseDtoMapper.toDto(userService.save(user));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        User user = userService.findById(id);
        userService.delete(user);
    }

    @GetMapping("/by-birthday")
    public List<UserResponseDto> findUserByBirthday(
            @Valid UserControllerFindByBirthdayParameters parameters, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException("Error validating parameters");
        }
        return userService.findByBirthday(parameters.from(), parameters.to()).stream()
                .map(userResponseDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
