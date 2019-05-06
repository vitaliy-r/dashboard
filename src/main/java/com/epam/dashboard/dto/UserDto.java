package com.epam.dashboard.dto;

import com.epam.dashboard.dto.validation.EnumValidator;
import com.epam.dashboard.dto.validation.EqualPasswords;
import com.epam.dashboard.dto.validation.IdValidator;
import com.epam.dashboard.dto.validation.UserUniqueFieldValidator;
import com.epam.dashboard.dto.validation.group.GeneralGroup;
import com.epam.dashboard.dto.validation.group.OnCreate;
import com.epam.dashboard.dto.validation.group.OnUpdate;
import com.epam.dashboard.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

import static com.epam.dashboard.model.enums.UserUniqueField.EMAIL;
import static com.epam.dashboard.model.enums.UserUniqueField.USERNAME;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@ApiModel(description = "User details")
@EqualPasswords(groups = OnCreate.class)
public class UserDto {

    @ApiModelProperty(notes = "The database generated board id")
    @JsonProperty(access = WRITE_ONLY)
    @Null(message = "User id must be null", groups = OnCreate.class)
    @NotBlank(message = "LastName cannot be null or empty", groups = OnUpdate.class)
    @IdValidator(message = "User is not found in database", dtoClass = UserDto.class, groups = OnUpdate.class)
    private String id;

    @ApiModelProperty(notes = "User's first name")
    @NotBlank(message = "FirstName cannot be null or empty", groups = GeneralGroup.class)
    private String firstName;

    @ApiModelProperty(notes = "User's last name")
    @NotBlank(message = "LastName cannot be null or empty", groups = GeneralGroup.class)
    private String lastName;

    @ApiModelProperty(notes = "User's gender. Can be one of 'MALE', 'FEMALE', 'UNKNOWN'")
    @EnumValidator(enumClass = Gender.class, groups = GeneralGroup.class)
    @NotBlank(message = "Email cannot be null or empty", groups = GeneralGroup.class)
    private String gender;

    @ApiModelProperty(notes = "User date of birth. Acceptable format: dd-MM-yyyy")
    @Pattern(message = "Date of birth format is not valid. Acceptable format: dd-MM-yyyy",
            groups = GeneralGroup.class, regexp = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$")
    @NotBlank(message = "Email cannot be null or empty", groups = GeneralGroup.class)
    private String dateOfBirth;

    @ApiModelProperty(notes = "Unique user's username")
    @NotBlank(message = "Email cannot be null or empty", groups = GeneralGroup.class)
    @UserUniqueFieldValidator(field = USERNAME, groups = OnCreate.class)
    private String username;

    @ApiModelProperty(notes = "Unique user's email")
    @Email(message = "Email is not valid")
    @NotBlank(message = "Email cannot be null or empty", groups = GeneralGroup.class)
    @UserUniqueFieldValidator(field = EMAIL, groups = OnCreate.class)
    private String email;

    @ApiModelProperty(notes = "User's password")
    @JsonProperty(access = WRITE_ONLY)
    @NotBlank(message = "Password cannot be null or empty", groups = OnCreate.class)
    private String password;

    @ApiModelProperty(notes = "Confirmation user's password. Should be equal to the first one")
    @JsonProperty(access = WRITE_ONLY)
    @NotBlank(message = "Password cannot be null or empty", groups = OnCreate.class)
    private String equalPassword;

}
