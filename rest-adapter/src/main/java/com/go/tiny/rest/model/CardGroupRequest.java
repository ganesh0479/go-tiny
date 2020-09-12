package com.go.tiny.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardGroupRequest {
  private String cardName;
  private String groupName;
  private String addedBy;
}
