package com.go.tiny.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TinyMail {
  private List<TinyMailRequest> mailRequests;
  private String to;
  private String from;
}
