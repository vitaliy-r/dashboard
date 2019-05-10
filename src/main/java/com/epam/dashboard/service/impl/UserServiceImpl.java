package com.epam.dashboard.service.impl;

import com.epam.dashboard.dto.UserDto;
import com.epam.dashboard.exception.InvalidIdException;
import com.epam.dashboard.exception.RecordIsNotFoundException;
import com.epam.dashboard.model.User;
import com.epam.dashboard.repository.UserRepository;
import com.epam.dashboard.service.UserService;
import com.epam.dashboard.mapper.UserMapper;
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
        validateId(id);

        User user = findUserByIdOrElseThrowException(id);

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
        User user = userRepository.insert(userMapper.mapUserDtoToUser(userDto));
        return userMapper.mapUserToUserDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.save(userMapper.mapUserDtoToUser(userDto));
        return userMapper.mapUserToUserDto(user);
    }

    @Override
    public void deleteById(String id) {
        validateId(id);

        User user = findUserByIdOrElseThrowException(id);
        userRepository.delete(user);
    }

    private void validateId(String id) {
        if (StringUtils.isBlank(id)) {
            throw new InvalidIdException("User id cannot be null or empty");
        }
    }

    private User findUserByIdOrElseThrowException(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RecordIsNotFoundException(String.format("User is not found by id: %s", id)));
    }

}
