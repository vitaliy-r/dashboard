package com.epam.dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Board {

    @Id
    private String id;
    @Indexed(unique = true)
    private String title;
    private String desc;
    private Integer maxSize;
    private List<Note> notes;
    private Metadata metadata;

    public void addNote(Note note) {
        if (Objects.isNull(notes)) {
            notes = new ArrayList<>();
        }
        notes.add(note);
    }

}
