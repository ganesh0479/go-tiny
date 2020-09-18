package com.go.tiny.rest.model;

import com.go.tiny.business.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupRoleRequest {
  @NotBlank
  @Size(min = 0, max = 10)
  private String groupName;

  private Role role;

  @NotBlank
  @Size(min = 0, max = 30)
  private String userName;

  private String addedBy;
}
