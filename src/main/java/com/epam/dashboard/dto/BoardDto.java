package com.epam.dashboard.dto;

import static com.epam.dashboard.service.impl.BoardServiceImpl.OBJECT_ID_REGEX;

import com.epam.dashboard.dto.validation.IdExists;
import com.epam.dashboard.dto.validation.UniqueBoardTitle;
import com.epam.dashboard.dto.validation.group.OnCreate;
import com.epam.dashboard.dto.validation.group.OnUpdate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
@ApiModel(description = "Board details")
public class BoardDto {

  @ApiModelProperty(notes = "The database generated board id")
  @Null(groups = OnCreate.class)
  @NotBlank(groups = OnUpdate.class)
  @Pattern(regexp = OBJECT_ID_REGEX, groups = OnUpdate.class)
  @IdExists(dtoClass = BoardDto.class, groups = OnUpdate.class)
  private String boardId;

  @ApiModelProperty(notes = "Unique board title")
  @NotBlank
  @UniqueBoardTitle(groups = OnCreate.class)
  private String title;

  @ApiModelProperty(notes = "Board description")
  @NotBlank
  private String desc;

  @ApiModelProperty(notes = "Maximum allowed number of board notes")
  @Positive
  @Max(value = 1000)
  private Integer maxSize;

}
