package com.go.tiny.rest.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
  private String name;

  @NotBlank
  @Size(min = 0, max = 50)
  private String emailId;

  @NotBlank
  @Size(min = 0, max = 50)
  private String password;

  private LocalDate createdOn;
}
