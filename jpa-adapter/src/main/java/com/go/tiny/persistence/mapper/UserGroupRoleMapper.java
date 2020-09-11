package com.go.tiny.persistence.mapper;

import com.go.tiny.business.model.UserGroupRole;
import com.go.tiny.persistence.entity.UserGroupRoleEntity;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public enum UserGroupRoleMapper {
  USER_GROUP_ROLE_MAPPER;

  public Optional<UserGroupRoleEntity> constructUserGroupRoleEntity(
      final UserGroupRole userGroupRole) {
    return ofNullable(
        UserGroupRoleEntity.builder()
            .groupName(userGroupRole.getGroupName())
            .role(userGroupRole.getRole().getRole())
            .userName(userGroupRole.getUserName())
            .addedBy(userGroupRole.getAddedBy())
            .build());
  }
}
