package com.go.tiny.persistence;

import com.go.tiny.business.constant.Role;
import com.go.tiny.business.model.UserGroupRole;
import com.go.tiny.business.port.ObtainUserGroupRole;
import com.go.tiny.persistence.dao.UserGroupRoleDao;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = GoTinyJpaTestConfiguration.class)
public class UserGroupRoleJpaAdapterTest {
  @Autowired private UserGroupRoleDao userGroupRoleDao;

  @Autowired
  @Qualifier("test-user-group-role-adapter")
  private ObtainUserGroupRole obtainUserGroupRole;

  @Test
  @DisplayName("should be able to update user group role with the support of database")
  public void shouldBeAbleToUpdateUserGroupRoleWithTheSupportOfStub() {
    // Given
    UserGroupRole userGroupRole = constructUserGroupRole();
    // When
    obtainUserGroupRole.updateUserGroupRole(userGroupRole);
    // Then
    assertThat(userGroupRoleDao.findAll()).isNotEmpty();
  }

  @Test
  @DisplayName("should not be able to update unknown user group role with the support of database")
  public void shouldNotBeAbleToUpdateUnknownUserGroupRoleWithTheSupportOfStub() {
    // When
    obtainUserGroupRole.updateUserGroupRole(null);
    // Then
    assertThat(TRUE);
  }

  private UserGroupRole constructUserGroupRole() {
    return UserGroupRole.builder()
        .groupName("TINY-GROUP")
        .role(Role.USER)
        .userName("TINY-USER")
        .addedBy("TINY-ADMIN-USER")
        .build();
  }
}
