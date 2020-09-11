package com.go.tiny.persistence;

import com.go.tiny.business.model.CardGroup;
import com.go.tiny.business.port.ObtainCardGroup;
import com.go.tiny.persistence.dao.CardGroupDao;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = GoTinyJpaTestConfiguration.class)
public class CardGroupJpaAdapterTest {
  @Autowired private CardGroupDao cardGroupDao;

  @Autowired
  @Qualifier("test-card-group-adapter")
  private ObtainCardGroup obtainCardGroup;

  @Test
  @DisplayName("should be able to add unknown card group with the support of database")
  public void shouldBeAbleToAddCardGroupWithTheSupportOfDatabase() {
    // Given
    CardGroup cardGroup = constructCardGroup();
    // When
    obtainCardGroup.add(cardGroup);
    // Then
    assertThat(cardGroupDao.findAll()).isNotEmpty();
  }

  @Test
  @DisplayName("should not be able to add unknown card group with the support of database")
  public void shouldNotBeAbleToAddCardGroupWithTheSupportOfDatabase() {
    // When
    obtainCardGroup.add(null);
    // Then
    assertThat(cardGroupDao.findAll()).isEmpty();
  }

  private CardGroup constructCardGroup() {
    return CardGroup.builder()
        .cardName("TINY-CARD")
        .groupName("TINY-GROUP")
        .addedBy("TINY-USER")
        .build();
  }
}
