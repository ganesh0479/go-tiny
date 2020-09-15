package com.go.tiny.persistence.adapter;

import com.go.tiny.business.model.UserGroupRole;
import com.go.tiny.business.port.ObtainUserGroupRole;
import com.go.tiny.persistence.dao.UserGroupRoleDao;
import com.go.tiny.persistence.entity.UserGroupRoleEntity;

import java.util.List;
import java.util.Optional;

import static com.go.tiny.persistence.mapper.UserGroupRoleMapper.USER_GROUP_ROLE_MAPPER;
import static java.util.Collections.emptyList;
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

  @Override
  public List<UserGroupRole> getGroups(final String userName) {
    if (isNull(userName)) {
      return emptyList();
    }
    List<UserGroupRoleEntity> userGroupRoleEntities = userGroupRoleDao.getByUserName(userName);
    return USER_GROUP_ROLE_MAPPER.constructUserGroupRoles(userGroupRoleEntities);
  }
}
