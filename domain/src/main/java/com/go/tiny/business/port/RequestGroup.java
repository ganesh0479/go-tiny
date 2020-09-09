package com.go.tiny.business.port;

import com.go.tiny.business.model.Group;

public interface RequestGroup {
  void create(Group group);

  void authorizeCardToDisplayInGroup(final String groupName, final String cardName);

  void approveCardChangesInTheGroup(final String groupName, final String cardName);
}
