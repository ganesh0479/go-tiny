package com.go.tiny.persistence.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_CARD_GROUP")
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
}
