package com.epam.dashboard.dto;

import com.epam.dashboard.dto.validation.IdValidator;
import com.epam.dashboard.dto.validation.UniqueTitleValidator;
import com.epam.dashboard.dto.validation.group.GeneralGroup;
import com.epam.dashboard.dto.validation.group.OnCreate;
import com.epam.dashboard.dto.validation.group.OnUpdate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;

@Data
@ApiModel(description = "Board details")
public class BoardDto {

    @ApiModelProperty(notes = "The database generated board id")
    @Null(message = "Board id must be null", groups = OnCreate.class)
    @NotBlank(message = "Board id must not be null or empty", groups = OnUpdate.class)
    @IdValidator(message = "Board is not found in database", dtoClass = BoardDto.class, groups = OnUpdate.class)
    private String boardId;

    @ApiModelProperty(notes = "Unique board title")
    @NotBlank(message = "Please, fill in title field", groups = GeneralGroup.class)
    @UniqueTitleValidator(dtoClass = BoardDto.class, groups = OnCreate.class)
    private String title;

    @ApiModelProperty(notes = "Board description")
    @NotBlank(message = "Please, fill in description field", groups = GeneralGroup.class)
    private String desc;

    @ApiModelProperty(notes = "Maximum allowed number of board notes")
    @Positive(message = "Size should be positive", groups = GeneralGroup.class)
    @Max(value = 1000, message = "Max size should be less than 1000", groups = GeneralGroup.class)
    private Integer maxSize;

}
