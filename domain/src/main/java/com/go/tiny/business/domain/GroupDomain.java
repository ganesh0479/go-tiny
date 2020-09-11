package com.go.tiny.business.domain;

import com.go.tiny.business.model.Group;
import com.go.tiny.business.port.ObtainGroup;
import com.go.tiny.business.port.RequestGroup;

import static com.go.tiny.business.helper.GroupHelper.GROUP_HELPER;

public class GroupDomain implements RequestGroup {
  private ObtainGroup obtainGroup;

  public GroupDomain(ObtainGroup obtainGroup) {
    this.obtainGroup = obtainGroup;
  }

  @Override
  public void create(final Group group) {
    GROUP_HELPER.initialize(obtainGroup);
    GROUP_HELPER.create(group);
  }

  @Override
  public void authorizeCardToDisplayInGroup(final String groupName, String cardName) {
    GROUP_HELPER.initialize(obtainGroup);
    GROUP_HELPER.authorizeCardToDisplayInGroup(groupName, cardName);
  }

  @Override
  public void approveCardChangesInTheGroup(final String groupName, String cardName) {
    GROUP_HELPER.initialize(obtainGroup);
    GROUP_HELPER.approveCardChangesInTheGroup(groupName, cardName);
  }
}
