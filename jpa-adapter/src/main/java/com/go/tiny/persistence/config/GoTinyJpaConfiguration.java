package com.go.tiny.persistence.config;

import com.go.tiny.business.port.ObtainGroup;
import com.go.tiny.business.port.ObtainCard;
import com.go.tiny.business.port.ObtainCardGroup;
import com.go.tiny.business.port.ObtainUser;
import com.go.tiny.business.port.ObtainUserGroupRole;

import com.go.tiny.persistence.adapter.CardGroupAdapter;
import com.go.tiny.persistence.adapter.CardAdapter;
import com.go.tiny.persistence.adapter.GroupAdapter;
import com.go.tiny.persistence.adapter.UserAdapter;
import com.go.tiny.persistence.adapter.UserGroupRoleAdapter;

import com.go.tiny.persistence.dao.CardDao;
import com.go.tiny.persistence.dao.CardGroupDao;
import com.go.tiny.persistence.dao.UserDao;
import com.go.tiny.persistence.dao.UserGroupRoleDao;
import com.go.tiny.persistence.dao.GroupDao;

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
