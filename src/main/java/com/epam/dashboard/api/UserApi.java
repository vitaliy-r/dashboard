package com.epam.dashboard.api;

import com.epam.dashboard.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@Api(tags = "User management REST API")
@ApiResponses({
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error")
})
public interface UserApi {

    @ApiOperation(value = "Get all users from database")
    @ApiResponse(code = 200, message = "OK", response = UserDto[].class)
    List<UserDto> getAllUsers();

    @ApiOperation(value = "Get one user from database",
            notes = "Search for and return user by provided id")
    @ApiResponse(code = 200, message = "OK", response = UserDto.class)
    UserDto getUser(String userId);

    @ApiOperation(value = "Create user from request parameter")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created", response = UserDto.class),
            @ApiResponse(code = 400, message = "Bad request")
    })
    UserDto createUser(UserDto userDto);

    @ApiOperation(value = "Update user from provided request dto parameter")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created", response = UserDto.class),
            @ApiResponse(code = 400, message = "Bad request")
    })
    UserDto updateUser(UserDto newUserDto);

    @ApiOperation(value = "Delete one user from database by provided id")
    @ApiResponse(code = 204, message = "No Content")
    void deleteById(String userId);

}
