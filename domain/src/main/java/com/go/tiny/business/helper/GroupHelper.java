package com.go.tiny.business.helper;

import com.go.tiny.business.model.Group;
import com.go.tiny.business.port.ObtainGroup;

public enum GroupHelper {
  GROUP_HELPER;
  private ObtainGroup obtainGroup;

  public void initialize(ObtainGroup obtainGroup) {
    this.obtainGroup = obtainGroup;
  }

  public void create(final Group group) {}

  public void authorizeCardToDisplayInGroup(final String groupName, final String cardName) {}

  public void approveCardChangesInTheGroup(final String groupName, final String cardName) {}
}
