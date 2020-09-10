package com.go.tiny.business;

import com.go.tiny.business.domain.UserGroupRoleDomain;
import com.go.tiny.business.exception.GoTinyDomainException;
import com.go.tiny.business.model.UserGroupRole;
import com.go.tiny.business.port.ObtainUserGroupRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.go.tiny.business.constant.Role.ADMIN;
import static com.go.tiny.business.exception.GoTinyDomainExceptionMessage.USER_GROUP_ROLE_RIGHT_SIDE_PORT_UNAVAILABLE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserGroupRoleDomainTest {
  @Mock private ObtainUserGroupRole obtainUserGroupRole;

  @Test
  @DisplayName("should be able to update user group role with the support of stub")
  void shouldBeAbleToUpdateUserGroupRoleWithTheSupportOfStub() {
    UserGroupRole userGroupRole = constructUserGroupRole();
    lenient().doNothing().when(obtainUserGroupRole).updateUserGroupRole(userGroupRole);
    UserGroupRoleDomain userGroupRoleDomain = new UserGroupRoleDomain(obtainUserGroupRole);
    userGroupRoleDomain.updateUserGroupRole(userGroupRole);
    verify(obtainUserGroupRole).updateUserGroupRole(userGroupRole);
  }

  @Test
  @DisplayName(
      "should throw an exception if right side port is not available while updating user group role")
  void shouldThrowAnExceptionIfRightSidePortIsNotAvailableWhileUpdatingUserGroupRole() {
    UserGroupRole userGroupRole = constructUserGroupRole();
    UserGroupRoleDomain userGroupRoleDomain = new UserGroupRoleDomain(null);
    assertThrows(
        GoTinyDomainException.class,
        () -> userGroupRoleDomain.updateUserGroupRole(userGroupRole),
        USER_GROUP_ROLE_RIGHT_SIDE_PORT_UNAVAILABLE);
  }

  private UserGroupRole constructUserGroupRole() {
    return UserGroupRole.builder()
        .groupName("TINY-GROUP")
        .role(ADMIN)
        .userName("TINY-USER")
        .addedBy("TINY-USER")
        .build();
  }
}
