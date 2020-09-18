package com.go.tiny.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TinyMailRequest {
  @NotBlank private String title;
  @NotBlank private String description;
  @NotBlank private String tinyUrl;
}
