package com.epam.dashboard.dto;

import com.epam.dashboard.dto.validation.IdValidator;
import com.epam.dashboard.dto.validation.UniqueTitleValidator;
import com.epam.dashboard.dto.validation.group.OnCreate;
import com.epam.dashboard.dto.validation.group.OnUpdate;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;

@Data
public class BoardDto {

    @Null(message = "Board id must be null", groups = OnCreate.class)
    @NotBlank(message = "Board id must not be null or empty", groups = OnUpdate.class)
    @IdValidator(message = "Board is not found in database", dtoClass = BoardDto.class, groups = OnUpdate.class)
    private String boardId;

    @NotBlank(message = "Please, fill in title field", groups = {OnCreate.class, OnUpdate.class})
    @UniqueTitleValidator(dtoClass = BoardDto.class, groups = OnCreate.class)
    private String title;

    @NotBlank(message = "Please, fill in description field", groups = {OnCreate.class, OnUpdate.class})
    private String desc;

    @Positive(message = "Size should be positive", groups = {OnCreate.class, OnUpdate.class})
    @Max(value = 1000, message = "Max size should be less than 1000", groups = {OnCreate.class, OnUpdate.class})
    private Integer maxSize;

}
