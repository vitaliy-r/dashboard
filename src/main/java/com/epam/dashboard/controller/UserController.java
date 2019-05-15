package com.epam.dashboard.controller;

import static org.springframework.http.HttpStatus.CREATED;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    log.info("getAllUsers: method is called");
    return ResponseEntity.ok(userResourceAssembler.toResource(userService.findAll()));
  }

  @GetMapping("/{userId}")
  @Override
  public ResponseEntity<UserResource> getUser(@PathVariable String userId) {
    log.info("getUser: called with userId parameter - {}", userId);
    return ResponseEntity.ok(userResourceAssembler.toResource(userService.findById(userId)));
  }

  @PostMapping
  @Override
  public ResponseEntity<UserResource> createUser(
      @RequestBody @Validated(OnCreate.class) UserDto userDto) {
    log.info("createUser: called with userDto request body - {}", userDto);
    return ResponseEntity.status(CREATED).body(
        userResourceAssembler.toResource(userService.createUser(userDto)));
  }

  @PutMapping
  @Override
  public ResponseEntity<UserResource> updateUser(
      @RequestBody @Validated(OnUpdate.class) UserDto newUserDto) {
    log.info("updateUser: called with newUserDto request body - {}", newUserDto);
    return ResponseEntity.status(CREATED).body(
        userResourceAssembler.toResource(userService.updateUser(newUserDto)));
  }

  @DeleteMapping("/{userId}")
  @Override
  public ResponseEntity<UserResource> deleteById(@PathVariable String userId) {
    log.info("deleteById: called with userId parameter  - {}", userId);
    userService.deleteById(userId);
    return ResponseEntity.ok(userResourceAssembler.toResource(new UserDto()));
  }

}
