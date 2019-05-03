package com.epam.dashboard.service.impl;

import com.epam.dashboard.dto.UserDto;
import com.epam.dashboard.repository.UserRepository;
import com.epam.dashboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto findById(String id) {
        if (StringUtils.isBlank(id)) {
            throw new InvalidParameterException("Id cannot be null or empty");
        }

        return
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        return null;
    }

    @Override
    public void deleteById(String id) {
        if (StringUtils.isBlank(id)) {
            throw new InvalidParameterException("Id cannot be null or empty");
        }

        userRepository.deleteById(id);
    }

}
