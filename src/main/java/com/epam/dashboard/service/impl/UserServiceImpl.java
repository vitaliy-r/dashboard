package com.epam.dashboard.service.impl;

import com.epam.dashboard.dto.UserDto;
import com.epam.dashboard.exception.InvalidIdException;
import com.epam.dashboard.exception.RecordIsNotFoundException;
import com.epam.dashboard.mapper.UserMapper;
import com.epam.dashboard.model.User;
import com.epam.dashboard.repository.UserRepository;
import com.epam.dashboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private static final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private final UserRepository userRepository;

    @Override
    public UserDto findById(String id) {
        User user = findUserByIdWithValidation(id);
        return userMapper.mapUserToUserDto(user);
    }

    @Override
    public List<UserDto> findAll() {
        return userMapper.mapUsersToUserDTOs(userRepository.findAll());
    }

    @Override
    public boolean isUserExistsWithEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public boolean isUserExistsWithUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.mapUserDtoToUser(userDto);
        userRepository.insert(user);

        userDto.setId(user.getId());

        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        findUserByIdWithValidation(userDto.getId());
        userRepository.save(userMapper.mapUserDtoToUser(userDto));
        return userDto;
    }

    @Override
    public void deleteById(String id) {
        User user = findUserByIdWithValidation(id);
        userRepository.delete(user);
    }

    private User findUserByIdWithValidation(String id) {
        if (StringUtils.isBlank(id)) {
            throw new InvalidIdException("User id cannot be null or empty");
        }

        return userRepository.findById(id)
                .orElseThrow(() -> new RecordIsNotFoundException(String.format("User is not found by id: %s", id)));
    }

}
