package com.go.tiny.business.helper;

import com.go.tiny.business.model.UserGroupRole;
import com.go.tiny.business.port.ObtainUserGroupRole;

public enum UserGroupRoleHelper {
  USER_GROUP_ROLE_HELPER;
  private ObtainUserGroupRole obtainUserGroupRole;

  public void initialize(ObtainUserGroupRole obtainUserGroupRole) {
    this.obtainUserGroupRole = obtainUserGroupRole;
  }

  public void updateUserGroupRole(UserGroupRole userGroupRole) {}
}
