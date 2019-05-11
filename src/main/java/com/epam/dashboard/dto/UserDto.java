package com.epam.dashboard.dto;

import com.epam.dashboard.dto.validation.EnumValidator;
import com.epam.dashboard.dto.validation.FieldMatch;
import com.epam.dashboard.dto.validation.IdValidator;
import com.epam.dashboard.dto.validation.UserUniqueFieldValidator;
import com.epam.dashboard.dto.validation.group.CommonGroup;
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
@FieldMatch(first = "password", second = "equalPassword",
        message = "The password fields must match", groups = OnCreate.class)
public class UserDto {

    @ApiModelProperty(notes = "The database generated board id")
    @Null(message = "User id must be null", groups = OnCreate.class)
    @NotBlank(message = "Use id cannot be null or empty", groups = OnUpdate.class)
    @IdValidator(message = "User is not found in database", dtoClass = UserDto.class, groups = OnUpdate.class)
    private String id;

    @ApiModelProperty(notes = "User's first name")
    @NotBlank(message = "Please, fill in 'firstName' field", groups = CommonGroup.class)
    private String firstName;

    @ApiModelProperty(notes = "User's last name")
    @NotBlank(message = "Please, fill in 'lastName' field", groups = CommonGroup.class)
    private String lastName;

    @ApiModelProperty(notes = "User's gender. Can be one of 'MALE', 'FEMALE', 'UNKNOWN'")
    @EnumValidator(enumClass = Gender.class, groups = CommonGroup.class)
    @NotBlank(message = "Please, fill in 'gender' field", groups = CommonGroup.class)
    private String gender;

    @ApiModelProperty(notes = "User date of birth. Acceptable format: dd-MM-yyyy")
    @Pattern(message = "Date of birth format is not valid. Acceptable format: dd-MM-yyyy",
            groups = CommonGroup.class, regexp = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$")
    @NotBlank(message = "Please, fill in 'dateOfBirth' field", groups = CommonGroup.class)
    private String dateOfBirth;

    @ApiModelProperty(notes = "Unique user's username")
    @NotBlank(message = "Please, fill in 'username' field", groups = CommonGroup.class)
    @UserUniqueFieldValidator(message = "Username is already is use", field = USERNAME, groups = OnCreate.class)
    private String username;

    @ApiModelProperty(notes = "Unique user's email")
    @Email(message = "Email is not valid", groups = CommonGroup.class,
            regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", flags = Pattern.Flag.CASE_INSENSITIVE)
    @NotBlank(message = "Please, fill in 'email' field", groups = CommonGroup.class)
    @UserUniqueFieldValidator(message = "Email is already is use", field = EMAIL, groups = OnCreate.class)
    private String email;

    @ApiModelProperty(notes = "User's password")
    @JsonProperty(access = WRITE_ONLY)
    @Null(message = "Password must be null", groups = OnUpdate.class)
    @NotBlank(message = "Please, fill in 'password' field", groups = OnCreate.class)
    private String password;

    @ApiModelProperty(notes = "Confirmation user's password. Should be equal to the first one")
    @JsonProperty(access = WRITE_ONLY)
    @Null(message = "Password must be null", groups = OnUpdate.class)
    @NotBlank(message = "Please, fill in 'equalPassword' field", groups = OnCreate.class)
    private String equalPassword;

}
