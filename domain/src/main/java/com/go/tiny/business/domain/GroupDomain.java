package com.go.tiny.business.domain;

import com.go.tiny.business.model.Group;
import com.go.tiny.business.port.ObtainGroup;

import static com.go.tiny.business.helper.GroupHelper.GROUP_HELPER;

public class GroupDomain {
  private ObtainGroup obtainGroup;

  public GroupDomain(ObtainGroup obtainGroup) {
    this.obtainGroup = obtainGroup;
  }

  public void create(final Group group) {
    GROUP_HELPER.initialize(obtainGroup);
    GROUP_HELPER.create(group);
  }

  public void authorizeCardToDisplayInGroup(final String groupName, String cardName) {
    GROUP_HELPER.initialize(obtainGroup);
    GROUP_HELPER.authorizeCardToDisplayInGroup(groupName, cardName);
  }

  public void approveCardChangesInTheGroup(final String groupName, String cardName) {
    GROUP_HELPER.initialize(obtainGroup);
    GROUP_HELPER.approveCardChangesInTheGroup(groupName, cardName);
  }
}
