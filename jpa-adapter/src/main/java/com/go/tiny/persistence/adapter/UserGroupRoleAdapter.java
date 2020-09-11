package com.go.tiny.persistence.adapter;

import com.go.tiny.business.model.UserGroupRole;
import com.go.tiny.business.port.ObtainUserGroupRole;
import com.go.tiny.persistence.dao.UserGroupRoleDao;
import com.go.tiny.persistence.entity.UserGroupRoleEntity;

import java.util.Optional;

import static com.go.tiny.persistence.mapper.UserGroupRoleMapper.USER_GROUP_ROLE_MAPPER;
import static java.util.Objects.isNull;

public class UserGroupRoleAdapter implements ObtainUserGroupRole {
  private UserGroupRoleDao userGroupRoleDao;

  public UserGroupRoleAdapter(UserGroupRoleDao userGroupRoleDao) {
    this.userGroupRoleDao = userGroupRoleDao;
  }

  @Override
  public void updateUserGroupRole(final UserGroupRole userGroupRole) {
    if (isNull(userGroupRole)) {
      return;
    }
    Optional<UserGroupRoleEntity> userGroupRoleEntity =
        USER_GROUP_ROLE_MAPPER.constructUserGroupRoleEntity(userGroupRole);
    userGroupRoleEntity.ifPresent(
        userGroupRoleEntityToSave -> userGroupRoleDao.save(userGroupRoleEntityToSave));
  }
}
