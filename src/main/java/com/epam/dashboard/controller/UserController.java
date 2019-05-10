package com.epam.dashboard.controller;

import com.epam.dashboard.api.UserApi;
import com.epam.dashboard.dto.UserDto;
import com.epam.dashboard.dto.validation.group.OnCreate;
import com.epam.dashboard.dto.validation.group.OnUpdate;
import com.epam.dashboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController implements UserApi {

    private final UserService userService;

    @GetMapping
    @Override
    public List<UserDto> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    @Override
    public UserDto getUser(@PathVariable String userId) {
        return userService.findById(userId);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Override
    public UserDto createUser(@RequestBody @Validated(OnCreate.class) UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PutMapping
    @ResponseStatus(CREATED)
    @Override
    public UserDto updateUser(@RequestBody @Validated(OnUpdate.class) UserDto newUserDto) {
        return userService.updateUser(newUserDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(NO_CONTENT)
    @Override
    public void deleteById(@PathVariable String userId) {
        userService.deleteById(userId);
    }

}
