package com.go.tiny.business.port;

import com.go.tiny.business.model.UserGroupRole;

import java.util.List;

public interface RequestUserGroupRole {
  void updateUserGroupRole(final UserGroupRole userGroupRole);

  List<UserGroupRole> getGroups(final String userName);
}
