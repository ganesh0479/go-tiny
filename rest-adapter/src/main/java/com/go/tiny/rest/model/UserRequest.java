package com.go.tiny.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
  private String name;
  private String emailId;
  private String password;
  private LocalDate createdOn;
}
