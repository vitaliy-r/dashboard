package com.epam.dashboard.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {

  private User creator;
  private LocalDateTime lastModifiedDate;
  private LocalDateTime creationDate;

}
