package com.epam.dashboard.controller;

import com.epam.dashboard.api.UserApi;
import com.epam.dashboard.controller.assembler.UserResourceAssembler;
import com.epam.dashboard.controller.resource.UserResource;
import com.epam.dashboard.dto.UserDto;
import com.epam.dashboard.dto.validation.group.OnCreate;
import com.epam.dashboard.dto.validation.group.OnUpdate;
import com.epam.dashboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController implements UserApi {

    private final UserService userService;
    private final UserResourceAssembler userResourceAssembler;

    @GetMapping
    @Override
    public ResponseEntity<Resources<UserResource>> getAllUsers() {
        return ResponseEntity.ok(userResourceAssembler.toResource(userService.findAll()));
    }

    @GetMapping("/{userId}")
    @Override
    public ResponseEntity<UserResource> getUser(@PathVariable String userId) {
        return ResponseEntity.ok(userResourceAssembler.toResource(userService.findById(userId)));
    }

    @PostMapping
    @Override
    public ResponseEntity<UserResource> createUser(@RequestBody @Validated(OnCreate.class) UserDto userDto) {
        return ResponseEntity.status(CREATED).body(
                userResourceAssembler.toResource(userService.createUser(userDto)));
    }

    @PutMapping
    @Override
    public ResponseEntity<UserResource> updateUser(@RequestBody @Validated(OnUpdate.class) UserDto newUserDto) {
        return ResponseEntity.status(CREATED).body(
                userResourceAssembler.toResource(userService.updateUser(newUserDto)));
    }

    @DeleteMapping("/{userId}")
    @Override
    public ResponseEntity<Void> deleteById(@PathVariable String userId) {
        userService.deleteById(userId);
        return new ResponseEntity<>(NO_CONTENT);
    }

}
