package com.go.tiny.persistence;

import com.go.tiny.business.port.ObtainCard;
import com.go.tiny.persistence.adapter.CardAdapter;
import com.go.tiny.persistence.dao.CardDao;
import com.go.tiny.persistence.dao.CardGroupDao;
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
}
