package com.epam.dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {

    private User creator;
    private LocalDate lastModifiedDate;
    private LocalDate creationDate;

}
