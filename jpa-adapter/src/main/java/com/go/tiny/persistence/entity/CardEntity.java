package com.go.tiny.persistence.entity;

import lombok.*;

import javax.persistence.*;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_CARD")
public class CardEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "TITLE")
  private String title;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "NAME")
  private String name;

  @Column(name = "ACTUAL_URL")
  private String actualUrl;

  @Column(name = "EXPIRES_IN")
  private Integer expiresIn;

  @Column(name = "CREATED_BY")
  private String createdBy;

  @Column(name = "TINY_URL")
  private String tinyUrl;

  @Column private byte[] picture;
}