package com.go.tiny.business;

import com.go.tiny.business.domain.CardGroupDomain;
import com.go.tiny.business.exception.GoTinyDomainException;
import com.go.tiny.business.model.CardGroup;
import com.go.tiny.business.port.ObtainCardGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.go.tiny.business.exception.GoTinyDomainExceptionMessage.CARD_GROUP_RIGHT_SIDE_PORT_UNAVAILABLE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CardGroupDomainTest {
  @Mock private ObtainCardGroup obtainCardGroup;

  @Test
  @DisplayName("should be able to add card group with the support of stub")
  void shouldBeAbleToAddCardGroupWithTheSupportOfStub() {
    CardGroup cardGroup = constructCardGroup();
    lenient().doNothing().when(obtainCardGroup).add(cardGroup);
    CardGroupDomain cardGroupDomain = new CardGroupDomain(obtainCardGroup);
    cardGroupDomain.add(cardGroup);
    verify(obtainCardGroup).add(cardGroup);
  }

  @Test
  @DisplayName(
      "should throw an exception if right side port is not available while adding card group")
  void shouldThrowAnExceptionIfRightSidePortIsNotAvailableWhileAddingCardGroup() {
    CardGroup cardGroup = constructCardGroup();
    CardGroupDomain cardGroupDomain = new CardGroupDomain(null);
    assertThrows(
        GoTinyDomainException.class,
        () -> cardGroupDomain.add(cardGroup),
        CARD_GROUP_RIGHT_SIDE_PORT_UNAVAILABLE);
  }

  private CardGroup constructCardGroup() {
    return CardGroup.builder()
        .cardName("TINY_CARD")
        .groupName("TINY_GROUP")
        .addedBy("TINY_USER")
        .build();
  }
}
