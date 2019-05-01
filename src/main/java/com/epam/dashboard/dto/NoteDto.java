package com.epam.dashboard.dto;

import com.epam.dashboard.dto.validation.EnumValidator;
import com.epam.dashboard.dto.validation.IdValidator;
import com.epam.dashboard.dto.validation.NoteGeneralValidator;
import com.epam.dashboard.dto.validation.group.OnCreate;
import com.epam.dashboard.dto.validation.group.OnUpdate;
import com.epam.dashboard.model.enums.NoteStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@NoteGeneralValidator(message = "Title is already in use", exists = false, groups = OnCreate.class)
@NoteGeneralValidator(message = "Note is not found by noteId", exists = true, groups = OnUpdate.class)
public class NoteDto {

    @JsonProperty(access = WRITE_ONLY)
    @NotBlank(message = "Board id must not be null or empty", groups = {OnCreate.class, OnUpdate.class})
    @IdValidator(message = "Board is not found in database",
            dtoClass = BoardDto.class, groups = {OnCreate.class, OnUpdate.class})
    private String boardId;

    @Null(message = "Note id must be null", groups = OnCreate.class)
    @NotBlank(message = "Note id must not be null or empty", groups = OnUpdate.class)
    private String noteId;

    @NotBlank(message = "Please, fill in title field", groups = {OnCreate.class, OnUpdate.class})
    private String title;

    @NotBlank(message = "Please, fill in content field", groups = {OnCreate.class, OnUpdate.class})
    private String content;

    @EnumValidator(enumClass = NoteStatus.class, groups = {OnCreate.class, OnUpdate.class})
    private String status;

    @Pattern(message = "Deadline format is not valid. Acceptable format: dd-MM-yyyy",
            groups = {OnCreate.class, OnUpdate.class}, regexp = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$")
    private String deadline;

}
