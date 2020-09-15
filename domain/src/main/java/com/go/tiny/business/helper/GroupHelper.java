package com.go.tiny.business.helper;

import com.go.tiny.business.exception.GoTinyDomainException;
import com.go.tiny.business.model.Group;
import com.go.tiny.business.port.ObtainGroup;

import static com.go.tiny.business.exception.GoTinyDomainExceptionMessage.GROUP_RIGHT_SIDE_PORT_UNAVAILABLE;
import static java.util.Objects.isNull;

public enum GroupHelper {
  GROUP_HELPER;
  private ObtainGroup obtainGroup;

  public void initialize(ObtainGroup obtainGroup) {
    this.obtainGroup = obtainGroup;
  }

  public Boolean create(final Group group) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(GROUP_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    return obtainGroup.create(group);
  }

  public void authorizeCardToDisplayInGroup(final String groupName, final String cardName) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(GROUP_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    obtainGroup.authorizeCardToDisplayInGroup(groupName, cardName);
  }

  public void approveCardChangesInTheGroup(final String groupName, final String cardName) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(GROUP_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    obtainGroup.approveCardChangesInTheGroup(groupName, cardName);
  }

  private Boolean isPortNotAvailable() {
    return isNull(obtainGroup);
  }
}
