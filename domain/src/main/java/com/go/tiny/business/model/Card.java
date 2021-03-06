package com.go.tiny.business.model;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
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
  private byte[] picture;
}
