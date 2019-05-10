package com.epam.dashboard.api;

import com.epam.dashboard.controller.resource.UserResource;
import com.epam.dashboard.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;

@Api(tags = "User management REST API")
@ApiResponses({
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error")
})
public interface UserApi {

    @ApiOperation(value = "Get all users from database")
    @ApiResponse(code = 200, message = "OK", response = UserResource[].class)
    ResponseEntity<Resources<UserResource>> getAllUsers();

    @ApiOperation(value = "Get one user from database",
            notes = "Search for and return user by provided id")
    @ApiResponse(code = 200, message = "OK", response = UserResource.class)
    ResponseEntity<UserResource> getUser(String userId);

    @ApiOperation(value = "Create user from request parameter")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created", response = UserResource.class),
            @ApiResponse(code = 400, message = "Bad request")
    })
    ResponseEntity<UserResource> createUser(UserDto userDto);

    @ApiOperation(value = "Update user from provided request dto parameter")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created", response = UserResource.class),
            @ApiResponse(code = 400, message = "Bad request")
    })
    ResponseEntity<UserResource> updateUser(UserDto newUserDto);

    @ApiOperation(value = "Delete one user from database by provided id")
    @ApiResponse(code = 204, message = "No Content")
    ResponseEntity<Void> deleteById(String userId);

}
