package com.epam.dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {

    private User creator;
    private LocalDateTime lastModifiedDate;
    private LocalDateTime creationDate;

}
