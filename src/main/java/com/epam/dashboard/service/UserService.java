package com.epam.dashboard.service;

import com.epam.dashboard.dto.UserDto;

public interface UserService {

    UserDto findById(String id);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    void deleteById(String id);

}
