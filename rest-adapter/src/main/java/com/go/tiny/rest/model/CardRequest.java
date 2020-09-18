package com.go.tiny.rest.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardRequest {
  @NotBlank private String title;
  private String description;

  @NotBlank
  @Size(min = 0, max = 10)
  private String name;

  @NotBlank private String actualUrl;
  private Integer expiresIn;
  private String createdBy;
  private String tinyUrl;
}
