package com.epam.dashboard.dto;

import com.epam.dashboard.dto.validation.EnumValidator;
import com.epam.dashboard.dto.validation.IdValidator;
import com.epam.dashboard.dto.validation.NoteGeneralValidator;
import com.epam.dashboard.dto.validation.group.CommonGroup;
import com.epam.dashboard.dto.validation.group.OnCreate;
import com.epam.dashboard.dto.validation.group.OnUpdate;
import com.epam.dashboard.model.enums.NoteStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@ApiModel(description = "Note details")
@NoteGeneralValidator(message = "Title is already in use", exists = false, groups = OnCreate.class)
@NoteGeneralValidator(message = "Note is not found by noteId", exists = true, groups = OnUpdate.class)
public class NoteDto {

    @ApiModelProperty(notes = "Id of board that current note belongs to")
    @JsonProperty(access = WRITE_ONLY)
    @NotBlank(message = "Board id must not be null or empty", groups = CommonGroup.class)
    @IdValidator(message = "Board is not found in database",
            dtoClass = BoardDto.class, groups = CommonGroup.class)
    private String boardId;

    @ApiModelProperty(notes = "Note generated id")
    @Null(message = "Note id must be null", groups = OnCreate.class)
    @NotBlank(message = "Note id must not be null or empty", groups = OnUpdate.class)
    private String noteId;

    @ApiModelProperty(notes = "Unique note title")
    @NotBlank(message = "Please, fill in title field", groups = CommonGroup.class)
    private String title;

    @ApiModelProperty(notes = "Note content")
    @NotBlank(message = "Please, fill in content field", groups = CommonGroup.class)
    private String content;

    @ApiModelProperty(notes = "Note status. One of 'TODO', 'COMPLETED' and 'EXPIRED'")
    @EnumValidator(enumClass = NoteStatus.class, groups = CommonGroup.class)
    private String status;

    @ApiModelProperty(notes = "Note completion date")
    @Pattern(message = "Deadline format is not valid. Acceptable format: dd-MM-yyyy",
            groups = CommonGroup.class, regexp = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$")
    private String deadline;

}
