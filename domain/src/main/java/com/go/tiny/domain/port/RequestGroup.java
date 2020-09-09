package com.go.tiny.domain.port;

import com.go.tiny.domain.model.Group;

public interface RequestGroup {
  void create(Group group);

  void authorizeCardToDisplayInGroup(final String groupName, final String cardName);

  void approveCardChangesInTheGroup(final String groupName, final String cardName);
}
