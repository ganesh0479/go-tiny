package com.go.tiny.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupRequest {
  @NotBlank
  @Size(min = 0, max = 10)
  private String name;

  private String createdBy;
}
