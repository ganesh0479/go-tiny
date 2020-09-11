package com.go.tiny.persistence;

import com.go.tiny.business.port.*;
import com.go.tiny.persistence.adapter.*;
import com.go.tiny.persistence.dao.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class GoTinyJpaTestConfiguration {
  @Bean
  @Qualifier("test-card-adapter")
  public ObtainCard getTestCardJpaAdapter(final CardDao cardDao, final CardGroupDao cardGroupDao) {
    return new CardAdapter(cardDao, cardGroupDao);
  }

  @Bean
  @Qualifier("test-card-group-adapter")
  public ObtainCardGroup getCardGroupJpaAdapter(final CardGroupDao cardGroupDao) {
    return new CardGroupAdapter(cardGroupDao);
  }

  @Bean
  @Qualifier("test-group-adapter")
  public ObtainGroup getGroupJpaAdapter(
      final GroupDao groupDao, final CardGroupDao cardGroupDao, final CardDao cardDao) {
    return new GroupAdapter(groupDao, cardGroupDao, cardDao);
  }

  @Bean
  @Qualifier("test-user-adapter")
  public ObtainUser getUserJpaAdapter(final UserDao userDao) {
    return new UserAdapter(userDao);
  }

  @Bean
  @Qualifier("test-user-group-role-adapter")
  public ObtainUserGroupRole getUserGroupRoleJpaAdapter(final UserGroupRoleDao userGroupRoleDao) {
    return new UserGroupRoleAdapter(userGroupRoleDao);
  }
}
