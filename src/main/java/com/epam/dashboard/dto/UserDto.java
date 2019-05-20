package com.epam.dashboard.dto;

import static com.epam.dashboard.model.enums.UserUniqueField.EMAIL;
import static com.epam.dashboard.model.enums.UserUniqueField.USERNAME;
import static com.epam.dashboard.service.impl.BoardServiceImpl.OBJECT_ID_REGEX;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

import com.epam.dashboard.dto.validation.Enum;
import com.epam.dashboard.dto.validation.FieldMatch;
import com.epam.dashboard.dto.validation.IdExists;
import com.epam.dashboard.dto.validation.UniqueUserField;
import com.epam.dashboard.dto.validation.group.OnCreate;
import com.epam.dashboard.dto.validation.group.OnUpdate;
import com.epam.dashboard.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
@ApiModel(description = "User details")
@FieldMatch(first = "password", second = "equalPassword", groups = OnCreate.class)
public class UserDto {

  @ApiModelProperty(notes = "The database generated board id")
  @Null(groups = OnCreate.class)
  @NotBlank(groups = OnUpdate.class)
  @Pattern(regexp = OBJECT_ID_REGEX, groups = OnUpdate.class)
  @IdExists(dtoClass = UserDto.class, groups = OnUpdate.class)
  private String id;

  @ApiModelProperty(notes = "User's first name")
  @NotBlank
  private String firstName;

  @ApiModelProperty(notes = "User's last name")
  @NotBlank
  private String lastName;

  @ApiModelProperty(notes = "User's gender. Can be one of 'MALE', 'FEMALE', 'UNKNOWN'")
  @NotBlank
  @Enum(enumClass = Gender.class)
  private String gender;

  @ApiModelProperty(notes = "User date of birth. Acceptable format: dd-MM-yyyy")
  @Pattern(regexp = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$")
  @NotBlank
  private String dateOfBirth;

  @ApiModelProperty(notes = "Unique user's username")
  @NotBlank
  @UniqueUserField(field = USERNAME, groups = OnCreate.class)
  private String username;

  @ApiModelProperty(notes = "Unique user's email")
  @Email(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
      flags = Pattern.Flag.CASE_INSENSITIVE)
  @NotBlank
  @UniqueUserField(field = EMAIL, groups = OnCreate.class)
  private String email;

  @ApiModelProperty(notes = "User's password")
  @JsonProperty(access = WRITE_ONLY)
  @Null(groups = OnUpdate.class)
  @NotBlank(groups = OnCreate.class)
  private String password;

  @ApiModelProperty(notes = "Confirmation user's password. Should be equal to the first one")
  @JsonProperty(access = WRITE_ONLY)
  @Null(groups = OnUpdate.class)
  @NotBlank(groups = OnCreate.class)
  private String equalPassword;

}
