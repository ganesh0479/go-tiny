package com.go.tiny.persistence.config;

import com.go.tiny.business.port.*;
import com.go.tiny.persistence.adapter.*;
import com.go.tiny.persistence.dao.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoTinyJpaConfiguration {

  @Bean
  @Qualifier("card-adapter")
  public ObtainCard getCardJpaAdapter(final CardDao cardDao, final CardGroupDao cardGroupDao) {
    return new CardAdapter(cardDao, cardGroupDao);
  }

  @Bean
  @Qualifier("card-group-adapter")
  public ObtainCardGroup getCardGroupJpaAdapter(final CardGroupDao cardGroupDao) {
    return new CardGroupAdapter(cardGroupDao);
  }

  @Bean
  @Qualifier("group-adapter")
  public ObtainGroup getGroupJpaAdapter(
      final GroupDao groupDao, final CardGroupDao cardGroupDao, final CardDao cardDao) {
    return new GroupAdapter(groupDao, cardGroupDao, cardDao);
  }

  @Bean
  @Qualifier("user-adapter")
  public ObtainUser getUserJpaAdapter(final UserDao userDao) {
    return new UserAdapter(userDao);
  }

  @Bean
  @Qualifier("user-group-role-adapter")
  public ObtainUserGroupRole getUserGroupRoleJpaAdapter(final UserGroupRoleDao userGroupRoleDao) {
    return new UserGroupRoleAdapter(userGroupRoleDao);
  }
}
