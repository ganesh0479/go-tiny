package com.go.tiny.business.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardGroup {
  private String cardName;
  private String groupName;
  private String addedBy;
  private Integer expiresIn;
  private String actualUrl;
  private String tinyUrl;
  private LocalDateTime createdTime;
}
