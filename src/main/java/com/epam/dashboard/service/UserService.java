package com.epam.dashboard.service;

import com.epam.dashboard.dto.UserDto;

public interface UserService {

    UserDto findById(String id);

}
