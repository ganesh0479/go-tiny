package com.go.tiny.persistence.mapper;

import com.go.tiny.business.model.UserGroupRole;
import com.go.tiny.persistence.entity.UserGroupRoleEntity;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
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

  private UserGroupRole constructUserGroupRole(final UserGroupRoleEntity userGroupRoleEntity) {
    return UserGroupRole.builder()
        .groupName(userGroupRoleEntity.getGroupName())
        .userName(userGroupRoleEntity.getUserName())
        .addedBy(userGroupRoleEntity.getAddedBy())
        .build();
  }

  public List<UserGroupRole> constructUserGroupRoles(
      final List<UserGroupRoleEntity> userGroupRoleEntities) {
    if (CollectionUtils.isEmpty(userGroupRoleEntities)) {
      return emptyList();
    }
    return userGroupRoleEntities.stream()
        .filter(Objects::nonNull)
        .map(this::constructUserGroupRole)
        .collect(Collectors.toList());
  }
}
