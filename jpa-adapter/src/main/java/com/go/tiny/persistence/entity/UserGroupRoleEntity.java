package com.go.tiny.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_USER_GROUP_ROLE")
public class UserGroupRoleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "GROUP_NAME")
  private String groupName;

  @Column(name = "ROLE")
  private String role;

  @Column(name = "USER_NAME")
  private String userName;

  @Column(name = "ADDED_BY")
  private String addedBy;
}
