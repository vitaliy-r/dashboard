package com.epam.dashboard.service.impl;

import com.epam.dashboard.dto.UserDto;
import com.epam.dashboard.exception.InvalidIdException;
import com.epam.dashboard.exception.RecordIsNotFoundException;
import com.epam.dashboard.model.User;
import com.epam.dashboard.model.enums.Gender;
import com.epam.dashboard.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository repository;

    private User testUser;
    private UserDto testUserDto;

    @Before
    public void initTestData() {
        testUser = generateUser();
        testUserDto = generateUserDto();
    }

    @Test
    public void findByIdTest() {
        when(repository.findById(anyString())).thenReturn(Optional.of(testUser));
        assertEquals(testUser.getId(), userService.findById("TestID").getId());
    }

    @Test
    public void findAllTest() {
        when(repository.findAll()).thenReturn(Collections.singletonList(testUser));
        assertEquals(testUser.getId(), userService.findAll().get(0).getId());
    }

    @Test
    public void isUserExistsWithEmailTest() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        assertTrue(userService.isUserExistsWithEmail(anyString()));
    }

    @Test
    public void isUserExistsWithUsername() {
        when(repository.findByUsername(anyString())).thenReturn(Optional.of(testUser));
        assertTrue(userService.isUserExistsWithUsername(anyString()));
    }

    @Test
    public void createUser() {
        when(repository.insert(any(User.class))).thenReturn(null);

        assertEquals(testUserDto.getId(), userService.createUser(testUserDto).getId());
        verify(repository).insert(any(User.class));
    }

    @Test
    public void updateUser() {
        when(repository.save(any(User.class))).thenReturn(null);

        assertEquals(testUserDto.getId(), userService.updateUser(testUserDto).getId());
        verify(repository).save(any(User.class));
    }

    @Test
    public void deleteById() {
        when(repository.findById(anyString())).thenReturn(Optional.of(testUser));
        doNothing().when(repository).delete(any());

        userService.deleteById("TestID");
        verify(repository).delete(any());
        assertEquals(testUser.getId(), userService.findById("TestID").getId());
    }

    @Test(expected = InvalidIdException.class)
    public void validateIdTest() throws Exception {
        Whitebox.invokeMethod(userService, "validateId", "");
    }

    @Test(expected = RecordIsNotFoundException.class)
    public void findUserByIdOrElseThrowExceptionTest() throws Exception {
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        Whitebox.invokeMethod(userService, "findUserByIdOrElseThrowException", "");
    }

    private static User generateUser() {
        User user = new User();
        user.setId("5cd601825b36631c48036439");
        user.setFirstName("Test first name");
        user.setLastName("Test last name");
        user.setGender(Gender.MALE);
        user.setDateOfBirth(LocalDate.parse("01-01-2000", ofPattern("dd-MM-yyyy")));
        user.setEmail("testemail@gmail.com");
        user.setUsername("Test unique username");
        user.setPassword("Password hash");

        return user;
    }

    private static UserDto generateUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId("5cd601825b36631c48036439");
        userDto.setFirstName("Test first name");
        userDto.setLastName("Test last name");
        userDto.setGender("Male");
        userDto.setDateOfBirth("01-01-2000");
        userDto.setEmail("testemail@gmail.com");
        userDto.setUsername("Test unique username");

        return userDto;
    }

}