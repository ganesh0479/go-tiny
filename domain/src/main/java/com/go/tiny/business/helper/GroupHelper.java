package com.go.tiny.business.helper;

import com.go.tiny.business.model.Group;
import com.go.tiny.business.port.ObtainGroup;

import static java.util.Objects.isNull;

public enum GroupHelper {
  GROUP_HELPER;
  private ObtainGroup obtainGroup;

  public void initialize(ObtainGroup obtainGroup) {
    this.obtainGroup = obtainGroup;
  }

  public void create(final Group group) {
    if (isPortNotAvailable()) {
      throw new NullPointerException();
    }
    obtainGroup.create(group);
  }

  public void authorizeCardToDisplayInGroup(final String groupName, final String cardName) {
    if (isPortNotAvailable()) {
      throw new NullPointerException();
    }
    obtainGroup.authorizeCardToDisplayInGroup(groupName, cardName);
  }

  public void approveCardChangesInTheGroup(final String groupName, final String cardName) {
    if (isPortNotAvailable()) {
      throw new NullPointerException();
    }
    obtainGroup.approveCardChangesInTheGroup(groupName, cardName);
  }

  private Boolean isPortNotAvailable() {
    return isNull(obtainGroup);
  }
}
