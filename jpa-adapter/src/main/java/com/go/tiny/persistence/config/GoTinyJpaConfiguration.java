package com.go.tiny.persistence.config;

import com.go.tiny.business.port.ObtainCard;
import com.go.tiny.persistence.adapter.CardAdapter;
import com.go.tiny.persistence.dao.CardDao;
import com.go.tiny.persistence.dao.CardGroupDao;
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
}
