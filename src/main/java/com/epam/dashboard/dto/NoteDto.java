package com.epam.dashboard.dto;

import static com.epam.dashboard.service.impl.BoardServiceImpl.OBJECT_ID_REGEX;

import com.epam.dashboard.dto.validation.Enum;
import com.epam.dashboard.dto.validation.IdExists;
import com.epam.dashboard.dto.validation.NoteIdExists;
import com.epam.dashboard.dto.validation.UniqueNoteTitle;
import com.epam.dashboard.dto.validation.group.OnCreate;
import com.epam.dashboard.dto.validation.group.OnUpdate;
import com.epam.dashboard.model.enums.NoteStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
@ApiModel(description = "Note details")
@NoteIdExists(groups = OnUpdate.class)
@UniqueNoteTitle(groups = OnCreate.class)
public class NoteDto {

  @ApiModelProperty(notes = "Id of board that current note belongs to")
  @NotBlank
  @Pattern(regexp = OBJECT_ID_REGEX)
  @IdExists(dtoClass = BoardDto.class)
  private String boardId;

  @ApiModelProperty(notes = "Note generated id")
  @Null(groups = OnCreate.class)
  @NotBlank(groups = OnUpdate.class)
  @Pattern(regexp = OBJECT_ID_REGEX, groups = OnUpdate.class)
  private String noteId;

  @ApiModelProperty(notes = "Unique note title")
  @NotBlank
  private String title;

  @ApiModelProperty(notes = "Note content")
  @NotBlank
  private String content;

  @ApiModelProperty(notes = "Note status. One of 'TODO', 'COMPLETED' and 'EXPIRED'")
  @Enum(enumClass = NoteStatus.class)
  private String status;

  @ApiModelProperty(notes = "Note completion date")
  @Pattern(regexp = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$")
  private String deadline;

}
