package com.go.tiny.persistence.config;

import com.go.tiny.business.port.ObtainCard;
import com.go.tiny.persistence.adapter.CardAdapter;
import com.go.tiny.persistence.dao.CardDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaAdapterConfiguration {
  @Bean
  public ObtainCard getCardAdapter(CardDao cardDao) {
    return new CardAdapter(cardDao);
  }
}
