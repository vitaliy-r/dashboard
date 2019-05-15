package com.epam.dashboard.model;

import com.epam.dashboard.model.enums.Gender;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class User {

  @Id
  private String id;
  private String firstName;
  private String lastName;
  private Gender gender;
  private LocalDate dateOfBirth;
  @Indexed(unique = true)
  private String username;
  @Indexed(unique = true)
  private String email;
  private String password;

}
