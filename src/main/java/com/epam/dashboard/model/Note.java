package com.epam.dashboard.model;

import com.epam.dashboard.model.enums.NoteStatus;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {

  private String id;
  private String title;
  private String content;
  private NoteStatus status;
  private LocalDate deadline;
  private Metadata metadata;

}
