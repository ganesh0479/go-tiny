package com.go.tiny.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardRequest {
  private String title;
  private String description;
  private String name;
  private String actualUrl;
  private Integer expiresIn;
  private String createdBy;
  private String tinyUrl;
  //private MultipartFile picture;
}
