package com.go.tiny.business.domain;

import com.go.tiny.business.model.UserGroupRole;
import com.go.tiny.business.port.ObtainUserGroupRole;

import static com.go.tiny.business.helper.UserGroupRoleHelper.USER_GROUP_ROLE_HELPER;

public class UserGroupRoleDomain {
  private ObtainUserGroupRole obtainUserGroupRole;

  public UserGroupRoleDomain(ObtainUserGroupRole obtainUserGroupRole) {
    this.obtainUserGroupRole = obtainUserGroupRole;
  }

  public void updateUserGroupRole(final UserGroupRole userGroupRole) {
    USER_GROUP_ROLE_HELPER.initialize(obtainUserGroupRole);
    USER_GROUP_ROLE_HELPER.updateUserGroupRole(userGroupRole);
  }
}
