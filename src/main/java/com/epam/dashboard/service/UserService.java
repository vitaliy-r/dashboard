package com.epam.dashboard.service;

import com.epam.dashboard.dto.UserDto;
import java.util.List;

public interface UserService {

  UserDto findById(String id);

  List<UserDto> findAll();

  boolean isUserExistsWithEmail(String email);

  boolean isUserExistsWithUsername(String username);

  UserDto createUser(UserDto userDto);

  UserDto updateUser(UserDto userDto);

  void deleteById(String id);

}
