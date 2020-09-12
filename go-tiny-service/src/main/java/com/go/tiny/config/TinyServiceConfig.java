package com.go.tiny.config;

import com.go.tiny.business.domain.*;
import com.go.tiny.business.port.*;
import com.go.tiny.persistence.config.GoTinyJpaConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(GoTinyJpaConfiguration.class)
public class TinyServiceConfig {
  @Bean
  public RequestCard getRequestCard(final ObtainCard obtainCard) {
    return new CardDomain(obtainCard);
  }

  @Bean
  public RequestCardGroup getRequestCardGroup(final ObtainCardGroup obtainCardGroup) {
    return new CardGroupDomain(obtainCardGroup);
  }

  @Bean
  public RequestUser getRequestUser(final ObtainUser obtainUser) {
    return new UserDomain(obtainUser);
  }

  @Bean
  public RequestGroup getRequestGroup(final ObtainGroup obtainGroup) {
    return new GroupDomain(obtainGroup);
  }

  @Bean
  public RequestUserGroupRole getRequestUserGroupRole(
      final ObtainUserGroupRole obtainUserGroupRole) {
    return new UserGroupRoleDomain(obtainUserGroupRole);
  }
}
