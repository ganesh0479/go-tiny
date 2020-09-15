package com.go.tiny.business.helper;

import com.go.tiny.business.exception.GoTinyDomainException;
import com.go.tiny.business.model.UserGroupRole;
import com.go.tiny.business.port.ObtainUserGroupRole;

import java.util.List;

import static com.go.tiny.business.exception.GoTinyDomainExceptionMessage.USER_GROUP_ROLE_RIGHT_SIDE_PORT_UNAVAILABLE;
import static java.util.Objects.isNull;

public enum UserGroupRoleHelper {
  USER_GROUP_ROLE_HELPER;
  private ObtainUserGroupRole obtainUserGroupRole;

  public void initialize(ObtainUserGroupRole obtainUserGroupRole) {
    this.obtainUserGroupRole = obtainUserGroupRole;
  }

  public void updateUserGroupRole(final UserGroupRole userGroupRole) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(USER_GROUP_ROLE_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    obtainUserGroupRole.updateUserGroupRole(userGroupRole);
  }

  public List<UserGroupRole> getGroups(final String userName) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(USER_GROUP_ROLE_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    return obtainUserGroupRole.getGroups(userName);
  }

  private Boolean isPortNotAvailable() {
    return isNull(obtainUserGroupRole);
  }
}
