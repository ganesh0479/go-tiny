package com.go.tiny.business.port;

import com.go.tiny.business.model.Group;

public interface ObtainGroup {
  void create(final Group group);

  void authorizeCardToDisplayInGroup(final String groupName, final String cardName);

  void approveCardChangesInTheGroup(final String groupName, final String cardName);
}
