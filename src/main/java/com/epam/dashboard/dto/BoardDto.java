package com.epam.dashboard.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BoardDto {

    @NotNull(message = "Title is mandatory")
    private String title;
    @NotNull(message = "Description is mandatory")
    private String desc;
    private Integer maxSize;

}
