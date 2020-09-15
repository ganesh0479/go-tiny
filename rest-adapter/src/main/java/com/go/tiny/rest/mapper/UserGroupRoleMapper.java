package com.go.tiny.rest.mapper;

import com.go.tiny.business.model.UserGroupRole;
import com.go.tiny.rest.model.UserGroupRoleRequest;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

  private UserGroupRoleRequest constructUserGroupRoleResponse(final UserGroupRole userGroupRole) {
    return UserGroupRoleRequest.builder()
        .userName(userGroupRole.getUserName())
        .addedBy(userGroupRole.getAddedBy())
        .role(userGroupRole.getRole())
        .groupName(userGroupRole.getGroupName())
        .build();
  }

  public List<UserGroupRoleRequest> constructUSerGroupRolesResponse(
      final List<UserGroupRole> userGroupRoles) {
    if (CollectionUtils.isEmpty(userGroupRoles)) {
      return Collections.emptyList();
    }
    return userGroupRoles.stream()
        .map(this::constructUserGroupRoleResponse)
        .collect(Collectors.toList());
  }
}
