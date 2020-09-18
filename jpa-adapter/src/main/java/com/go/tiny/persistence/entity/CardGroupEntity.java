package com.go.tiny.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "T_CARD_GROUP",
    indexes = {@Index(name = "t_card_group_index", columnList = "CARD_NAME,GROUP_NAME")})
public class CardGroupEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "CARD_NAME")
  private String cardName;

  @Column(name = "GROUP_NAME")
  private String groupName;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "ADDED_BY")
  private String addedBy;

  @Column(name = "TINY_URL")
  private String tinyUrl;

  @Column(name = "ACTUAL_URL")
  private String actualUrl;

  @Column(name = "EXPIRES_IN")
  private Integer expiresIn;

  @Column(name = "CREATED_TIME")
  private LocalDateTime createdTime;
}
