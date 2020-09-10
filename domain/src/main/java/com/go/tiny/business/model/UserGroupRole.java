package com.go.tiny.business.model;

import com.go.tiny.business.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupRole {
  private String groupName;
  private Role role;
  private String userName;
  private String addedBy;
}
