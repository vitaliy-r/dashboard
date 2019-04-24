package com.epam.dashboard.model;

import com.epam.dashboard.model.enums.NoteStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Note {

    @Id
    public String id;
    @Indexed(unique = true)
    private String title;
    private String content;
    private NoteStatus status;
    private LocalDate deadline;
    private Metadata metadata;

}
