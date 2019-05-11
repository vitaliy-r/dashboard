package com.epam.dashboard.controller;

import com.epam.dashboard.controller.assembler.UserResourceAssembler;
import com.epam.dashboard.controller.resource.UserResource;
import com.epam.dashboard.dto.UserDto;
import com.epam.dashboard.exception.InvalidIdException;
import com.epam.dashboard.exception.ServiceException;
import com.epam.dashboard.service.BoardService;
import com.epam.dashboard.service.UserService;
import com.google.gson.Gson;
import com.mongodb.MongoException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.epam.dashboard.model.enums.ErrorCode.*;
import static com.epam.dashboard.model.enums.ErrorType.FATAL_ERROR_TYPE;
import static com.epam.dashboard.model.enums.ErrorType.PROCESSING_ERROR_TYPE;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    private static final MediaType HAL_JSON_CONTENT_TYPE = MediaType.valueOf("application/hal+json;charset=UTF-8");
    private static Gson mapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private BoardService boardService;
    @MockBean
    private UserResourceAssembler resourceAssembler;

    private UserDto testUser;
    private UserResource testUserResource;
    private Resources<UserResource> testUserResources;

    @BeforeClass
    public static void initGlobal() {
        mapper = new Gson();
    }

    @Before
    public void initEach() {
        testUser = generateUserDto();
        testUserResource = generateNoteResource();
        testUserResources = generateNoteResources();
    }

    @Test
    public void getAllUsersTest() throws Exception {
        when(userService.findAll()).thenReturn(Collections.singletonList(testUser));
        when(resourceAssembler.toResource(anyList())).thenReturn(testUserResources);

        this.mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(HAL_JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$._embedded.userResourceList.[0].id")
                        .value(testUserResources.getContent().iterator().next().getUserDto().getId()));
    }

    @Test
    public void getAllUsersMongoExceptionTest() throws Exception {
        String errorMsg = "Unexpected error";

        when(userService.findAll()).thenThrow(new MongoException(errorMsg));

        this.mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.type").value(PROCESSING_ERROR_TYPE.name()))
                .andExpect(jsonPath("$.code").value(MONGO_ERROR_CODE.name()))
                .andExpect(jsonPath("$.message").value(errorMsg));
    }

    @Test
    public void getAllUsersFatalExceptionTest() throws Exception {
        String errorMsg = "Fatal exception";

        when(userService.findAll()).thenThrow(new NullPointerException(errorMsg));

        this.mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.type").value(FATAL_ERROR_TYPE.name()))
                .andExpect(jsonPath("$.code").value(APPLICATION_ERROR_CODE.name()))
                .andExpect(jsonPath("$.message").value(errorMsg));
    }

    @Test
    public void getUserTest() throws Exception {
        when(userService.findById(anyString())).thenReturn(testUser);
        when(resourceAssembler.toResource(any(UserDto.class))).thenReturn(testUserResource);

        this.mockMvc.perform(get(String.format("/user/%s", testUser.getId())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(HAL_JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.id").value(testUser.getId()));
    }

    @Test
    public void getUserValidationExceptionTest() throws Exception {
        String errorMsg = "Id is invalid";

        when(userService.findById(anyString())).thenThrow(new InvalidIdException(errorMsg));

        this.mockMvc.perform(get(String.format("/user/%s", "Not valid id")))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.type").value(PROCESSING_ERROR_TYPE.name()))
                .andExpect(jsonPath("$.code").value(VALIDATION_ERROR_CODE.name()))
                .andExpect(jsonPath("$.message").value(errorMsg));
    }

    @Test
    public void createUserTest() throws Exception {
        // validation
        when(userService.isUserExistsWithEmail(anyString())).thenReturn(false); // unique email
        when(userService.isUserExistsWithUsername(anyString())).thenReturn(false); // unique username

        when(userService.createUser(any())).thenReturn(testUser);
        when(resourceAssembler.toResource(any(UserDto.class))).thenReturn(testUserResource);

        UserDto inputUserDto = generateUserDto();
        inputUserDto.setId(null);
        String password = "Passw0rd";
        inputUserDto.setPassword(password);
        inputUserDto.setEqualPassword(password);

        this.mockMvc.perform(post("/user")
                .contentType(APPLICATION_JSON)
                .content(mapper.toJson(inputUserDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(HAL_JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.id").value(testUser.getId()));
    }

    @Test
    public void createUserMethodArgumentNotValidExceptionTest() throws Exception {
        when(userService.isUserExistsWithEmail(anyString())).thenReturn(true); // not unique email
        when(userService.isUserExistsWithUsername(anyString())).thenReturn(true); // not unique username

        this.mockMvc.perform(post("/user")
                .contentType(APPLICATION_JSON)
                .content(mapper.toJson(testUser)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(5))) // id is not null, both password fields are empty
                .andExpect(jsonPath("$[0].type").value(PROCESSING_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[0].code").value(VALIDATION_ERROR_CODE.name()));
    }

    @Test
    public void updateUserTest() throws Exception {
        // validation
        when(userService.findById(anyString())).thenReturn(testUser); // user exists

        when(userService.updateUser(any())).thenReturn(testUser);
        when(resourceAssembler.toResource(any(UserDto.class))).thenReturn(testUserResource);

        this.mockMvc.perform(put("/user")
                .contentType(APPLICATION_JSON)
                .content(mapper.toJson(testUser)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(HAL_JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.id").value(testUser.getId()));
    }

    @Test
    public void deleteByIdTest() throws Exception {
        doNothing().when(userService).deleteById(anyString());
        when(resourceAssembler.toResource(any(UserDto.class)))
                .thenReturn(new UserResource(new UserDto()));

        this.mockMvc.perform(delete(String.format("/user/%s", "userId")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(HAL_JSON_CONTENT_TYPE));

        verify(userService).deleteById(anyString());
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

    private static UserResource generateNoteResource() {
        return new UserResource(generateUserDto());
    }

    private static Resources<UserResource> generateNoteResources() {
        return new Resources<>(Collections.singletonList(new UserResource(generateUserDto())));
    }

}