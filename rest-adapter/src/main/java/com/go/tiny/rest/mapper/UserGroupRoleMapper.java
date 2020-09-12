package com.go.tiny.rest.mapper;

import com.go.tiny.business.model.UserGroupRole;
import com.go.tiny.rest.model.UserGroupRoleRequest;

import static java.util.Objects.nonNull;

public enum UserGroupRoleMapper {
  USER_GROUP_ROLE_MAPPER;

  public UserGroupRole constructUserGroupRole(final UserGroupRoleRequest userGroupRoleRequest) {
    return nonNull(userGroupRoleRequest)
        ? UserGroupRole.builder()
            .userName(userGroupRoleRequest.getUserName())
            .addedBy(userGroupRoleRequest.getAddedBy())
            .role(userGroupRoleRequest.getRole())
            .groupName(userGroupRoleRequest.getGroupName())
            .build()
        : null;
  }
}
