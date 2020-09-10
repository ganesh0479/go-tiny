package com.go.tiny.business;

import com.go.tiny.business.domain.GroupDomain;
import com.go.tiny.business.exception.GoTinyDomainException;
import com.go.tiny.business.model.Group;
import com.go.tiny.business.port.ObtainGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.go.tiny.business.exception.GoTinyDomainExceptionMessage.GROUP_RIGHT_SIDE_PORT_UNAVAILABLE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GroupDomainTest {
  @Mock private ObtainGroup obtainGroup;
  private static final String groupName = "TINY-GROUP";
  private static final String cardName = "TINY-CARD";

  @Test
  @DisplayName("should be able to create group with the support of stub")
  void shouldBeAbleToCreateGroupWithTheSupportOfStub() {
    Group group = constructGroup();
    lenient().doNothing().when(obtainGroup).create(group);
    GroupDomain groupDomain = new GroupDomain(obtainGroup);
    groupDomain.create(group);
    verify(obtainGroup).create(group);
  }

  @Test
  @DisplayName("should throw an exception if right side port is not available while creating group")
  void shouldThrowAnExceptionIfRightSidePortIsNotAvailableWhileCreatingGroup() {
    Group group = constructGroup();
    GroupDomain groupDomain = new GroupDomain(null);
    assertThrows(
        GoTinyDomainException.class,
        () -> groupDomain.create(group),
        GROUP_RIGHT_SIDE_PORT_UNAVAILABLE);
  }

  @Test
  @DisplayName("should be able to authorize card to display in group with the support of stub")
  void shouldBeAbleToAuthorizeCardToDisplayInGroupWithTheSupportOfStub() {
    lenient().doNothing().when(obtainGroup).authorizeCardToDisplayInGroup(groupName, cardName);
    GroupDomain groupDomain = new GroupDomain(obtainGroup);
    groupDomain.authorizeCardToDisplayInGroup(groupName, cardName);
    verify(obtainGroup).authorizeCardToDisplayInGroup(groupName, cardName);
  }

  @Test
  @DisplayName(
      "should throw an exception if right side port is not available while authorizing card in group")
  void shouldThrowAnExceptionIfRightSidePortIsNotAvailableWhileAuthorizingCardInGroup() {
    GroupDomain groupDomain = new GroupDomain(null);
    assertThrows(
        GoTinyDomainException.class,
        () -> groupDomain.authorizeCardToDisplayInGroup(groupName, cardName),
        GROUP_RIGHT_SIDE_PORT_UNAVAILABLE);
  }

  @Test
  @DisplayName("should be able to approve card to display in group with the support of stub")
  void shouldBeAbleToApproveCardToDisplayInGroupWithTheSupportOfStub() {
    lenient().doNothing().when(obtainGroup).authorizeCardToDisplayInGroup(groupName, cardName);
    GroupDomain groupDomain = new GroupDomain(obtainGroup);
    groupDomain.approveCardChangesInTheGroup(groupName, cardName);
    verify(obtainGroup).approveCardChangesInTheGroup(groupName, cardName);
  }

  @Test
  @DisplayName(
      "should throw an exception if right side port is not available while approve card in group")
  void shouldThrowAnExceptionIfRightSidePortIsNotAvailableWhileApproveCardInGroup() {
    GroupDomain groupDomain = new GroupDomain(null);
    assertThrows(
        GoTinyDomainException.class,
        () -> groupDomain.approveCardChangesInTheGroup(groupName, cardName),
        GROUP_RIGHT_SIDE_PORT_UNAVAILABLE);
  }

  private Group constructGroup() {
    return Group.builder().name("TINY-GROUP").createdBy("TINY-USER").build();
  }
}
