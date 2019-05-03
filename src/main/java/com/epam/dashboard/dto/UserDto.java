package com.epam.dashboard.dto;

import com.epam.dashboard.dto.validation.EnumValidator;
import com.epam.dashboard.dto.validation.IdValidator;
import com.epam.dashboard.dto.validation.group.GeneralGroup;
import com.epam.dashboard.dto.validation.group.OnCreate;
import com.epam.dashboard.dto.validation.group.OnUpdate;
import com.epam.dashboard.model.enums.Gender;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(description = "User details")
@EqualPasswords(message = "Passwords should match", groups = OnCreate.class)
public class UserDto {

    @Null(message = "User id must be null", groups = OnCreate.class)
    @NotBlank(message = "LastName cannot be null or empty", groups = OnUpdate.class)
    @IdValidator(message = "User is not found in database", dtoClass = UserDto.class, groups = OnUpdate.class)
    private String id;

    @NotBlank(message = "FirstName cannot be null or empty", groups = GeneralGroup.class)
    private String firstName;

    @NotBlank(message = "LastName cannot be null or empty", groups = GeneralGroup.class)
    private String lastName;

    @EnumValidator(enumClass = Gender.class, groups = GeneralGroup.class)
    @NotBlank(message = "Email cannot be null or empty", groups = GeneralGroup.class)
    private String gender;

    @Pattern(message = "Date of birth format is not valid. Acceptable format: dd-MM-yyyy",
            groups = GeneralGroup.class, regexp = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$")
    @NotBlank(message = "Email cannot be null or empty", groups = GeneralGroup.class)
    private String dateOfBirth;

    @NotBlank(message = "Email cannot be null or empty", groups = GeneralGroup.class)
    @UniqueUsernameValidator(groups = GeneralGroup.class)
    private String username;

    @Email(message = "Email is not valid")
    @NotBlank(message = "Email cannot be null or empty", groups = GeneralGroup.class)
    private String email;

    @NotBlank(message = "Password cannot be null or empty", groups = OnCreate.class)
    private String password;

    @NotBlank(message = "Password cannot be null or empty", groups = OnCreate.class)
    private String equalPassword;

}
