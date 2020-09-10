package com.go.tiny.business.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Card {
  private String title;
  private String description;
  private String name;
  private String actualUrl;
  private Integer expiresIn;
  private String createdBy;
  private String tinyUrl;
}
