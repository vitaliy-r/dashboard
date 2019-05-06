package com.epam.dashboard.util;

import com.epam.dashboard.dto.UserDto;
import com.epam.dashboard.model.User;
import com.epam.dashboard.model.enums.Gender;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface UserMapper {

    @Mapping(target = "dateOfBirth", source = "dateOfBirth", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "equalPassword", ignore = true)
    UserDto mapUserToUserDto(User user);

    @Mapping(target = "gender", source = "userDto", qualifiedByName = "formGender")
    @Mapping(target = "dateOfBirth", source = "dateOfBirth", dateFormat = "dd-MM-yyyy")
    User mapUserDtoToUser(UserDto userDto);

    List<UserDto> mapUsersToUserDTOs(List<User> users);

    @Named("formGender")
    default Gender formGender(UserDto userDto) {
        for (Gender gender : Gender.values()) {
            if (gender.name().equalsIgnoreCase(userDto.getGender())) {
                return gender;
            }
        }
        return null;
    }

}
