package com.go.tiny.business;

import com.go.tiny.business.domain.CardDomain;
import com.go.tiny.business.exception.GoTinyDomainException;
import com.go.tiny.business.model.Card;
import com.go.tiny.business.port.ObtainCard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.go.tiny.business.exception.GoTinyDomainExceptionMessage.CARD_RIGHT_SIDE_PORT_UNAVAILABLE;
import static com.go.tiny.business.exception.GoTinyDomainExceptionMessage.INVALID_URL;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CardDomainTest {
  @Mock private ObtainCard obtainCard;
  private static final String CARD_NAME = "TINY-CARD";
  private static final String GROUP_NAME = "GROUP-CARD";
  private static final String APPROVAL_STATUS = "APPROVED";
  private static final String TINY_URL = "http://go-tiny.com/TINY-URL";
  private static final String ACTUAL_URL = "http://random.com/ahbjkjdjjsggdgjdbnjghjdnn";

  @Test
  @DisplayName("should be able to update card with the support of stub")
  void shouldBeAbleToUpdateCardWithTheSupportOfStub() {
    Card card = constructCard();
    lenient().doNothing().when(obtainCard).update(card);
    CardDomain cardDomain = new CardDomain(obtainCard);
    cardDomain.update(card);
    verify(obtainCard).update(card);
  }

  @Test
  @DisplayName("should throw an exception if right side port is not available while updating card")
  void shouldThrowAnExceptionIfRightSidePortIsNotAvailableWhileUpdatingCard() {
    Card card = constructCard();
    CardDomain cardDomain = new CardDomain(null);
    assertThrows(
        GoTinyDomainException.class,
        () -> cardDomain.update(card),
        CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
  }

  @Test
  @DisplayName("should be able to delete card with the support of stub")
  void shouldBeAbleToDeleteCardWithTheSupportOfStub() {
    lenient().doNothing().when(obtainCard).delete(CARD_NAME);
    CardDomain cardDomain = new CardDomain(obtainCard);
    cardDomain.delete(CARD_NAME);
    verify(obtainCard).delete(CARD_NAME);
  }

  @Test
  @DisplayName("should throw an exception if right side port is not available while deleting card")
  void shouldThrowAnExceptionIfRightSidePortIsNotAvailableWhileDeletingCard() {
    CardDomain cardDomain = new CardDomain(null);
    assertThrows(
        GoTinyDomainException.class,
        () -> cardDomain.delete(CARD_NAME),
        CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
  }

  @Test
  @DisplayName("should be able to get card with the support of stub")
  void shouldBeAbleToGetCardWithTheSupportOfStub() {
    Card card = constructCard();
    lenient().when(obtainCard.get(CARD_NAME)).thenReturn(of(card));
    CardDomain cardDomain = new CardDomain(obtainCard);
    Card responseCard = cardDomain.get(CARD_NAME);
    assertThat(responseCard)
        .extracting(
            "title", "description", "name", "actualUrl", "expiresIn", "createdBy", "tinyUrl")
        .contains(
            card.getTitle(),
            card.getDescription(),
            card.getName(),
            card.getActualUrl(),
            card.getExpiresIn(),
            card.getCreatedBy(),
            card.getTinyUrl());
    verify(obtainCard).get(CARD_NAME);
  }

  @Test
  @DisplayName("should be able to create card with the support of stub")
  void shouldBeAbleToCreateCardWithTheSupportOfStub() {
    Card card = constructCard();
    card.setTinyUrl(null);
    Card cardWithTinyUrl = constructCard();
    card.setTinyUrl(TINY_URL);
    lenient().when(obtainCard.create(card)).thenReturn(of(cardWithTinyUrl));
    lenient().when(obtainCard.getUniqueId()).thenReturn(System.currentTimeMillis());
    lenient().when(obtainCard.checkTinyUrlAlreadyExist(anyString(), any())).thenReturn(false);
    CardDomain cardDomain = new CardDomain(obtainCard);
    Card createCardResponse = cardDomain.create(card);
    assertThat(createCardResponse.getTinyUrl()).matches(TINY_URL);
    verify(obtainCard).getUniqueId();
    verify(obtainCard).create(card);
  }

  @Test
  @DisplayName("should be able to create card on existing tiny url with the support of stub")
  void shouldBeAbleToCreateCardOnExistingTinyUrlWithTheSupportOfStub() {
    Card card = constructCard();
    card.setTinyUrl(null);
    Card cardWithTinyUrl = constructCard();
    card.setTinyUrl(TINY_URL);
    Set<String> shortUrls = new HashSet<>();
    shortUrls.add("abcdefg");
    lenient().when(obtainCard.create(card)).thenReturn(of(cardWithTinyUrl));
    lenient().when(obtainCard.getUniqueId()).thenReturn(System.currentTimeMillis());
    lenient().when(obtainCard.checkTinyUrlAlreadyExist(anyString(), any())).thenReturn(true);
    lenient()
        .when(this.obtainCard.getAvailableShortURLsByFilteringExistingUrls(any(), any(Set.class)))
        .thenReturn(shortUrls);
    CardDomain cardDomain = new CardDomain(obtainCard);
    Card createCardResponse = cardDomain.create(card);
    assertThat(createCardResponse.getTinyUrl()).matches(TINY_URL);
    verify(obtainCard).getUniqueId();
    verify(obtainCard).create(card);
  }

  @Test
  @DisplayName(
      "should be able to create card with smaller unique id tiny url with the support of stub")
  void shouldBeAbleToCreateCardWithSmallerUniqueIdWithTheSupportOfStub() {
    Card card = constructCard();
    card.setTinyUrl(null);
    Card cardWithTinyUrl = constructCard();
    card.setTinyUrl(TINY_URL);
    Set<String> shortUrls = new HashSet<>();
    shortUrls.add("abcdefg");
    lenient().when(obtainCard.create(card)).thenReturn(of(cardWithTinyUrl));
    lenient().when(obtainCard.getUniqueId()).thenReturn(1L);
    lenient().when(obtainCard.checkTinyUrlAlreadyExist(anyString(), any())).thenReturn(true);
    lenient()
        .when(this.obtainCard.getAvailableShortURLsByFilteringExistingUrls(any(), any(Set.class)))
        .thenReturn(shortUrls);
    CardDomain cardDomain = new CardDomain(obtainCard);
    Card createCardResponse = cardDomain.create(card);
    assertThat(createCardResponse.getTinyUrl()).matches(TINY_URL);
    verify(obtainCard).getUniqueId();
    verify(obtainCard).create(card);
  }

  @Test
  @DisplayName("should throw an exception if right side port is not available while creating card")
  void shouldThrowAnExceptionIfRightSidePortIsNotAvailableWhileCreatingCard() {
    Card card = constructCard();
    CardDomain cardDomain = new CardDomain(null);
    assertThrows(
        GoTinyDomainException.class,
        () -> cardDomain.create(card),
        CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
  }

  @Test
  @DisplayName("should throw an exception if invalid url is given while creating card")
  void shouldThrowAnExceptionIfInvalidUrlIsGivenWhileCreatingCard() {
    Card card = constructCard();
    card.setActualUrl("TINY");
    CardDomain cardDomain = new CardDomain(obtainCard);
    assertThrows(GoTinyDomainException.class, () -> cardDomain.create(card), INVALID_URL);
  }

  @Test
  @DisplayName("should throw an exception if right side port is not available while fetching card")
  void shouldThrowAnExceptionIfRightSidePortIsNotAvailableWhileFetchingCard() {
    CardDomain cardDomain = new CardDomain(null);
    assertThrows(
        GoTinyDomainException.class,
        () -> cardDomain.get(CARD_NAME),
        CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
  }

  @Test
  @DisplayName("should be able to get all cards with the support of stub")
  void shouldBeAbleToGetAllCardsWithTheSupportOfStub() {
    Card card = constructCard();
    List<Card> cards = List.of(card);
    lenient().when(obtainCard.getCardsNotBelongToGroup()).thenReturn(cards);
    CardDomain cardDomain = new CardDomain(obtainCard);
    List<Card> fetchedCards = cardDomain.getCardsNotBelongToGroup();
    assertThat(fetchedCards.get(0))
        .extracting(
            "title", "description", "name", "actualUrl", "expiresIn", "createdBy", "tinyUrl")
        .contains(
            card.getTitle(),
            card.getDescription(),
            card.getName(),
            card.getActualUrl(),
            card.getExpiresIn(),
            card.getCreatedBy(),
            card.getTinyUrl());
    verify(obtainCard).getCardsNotBelongToGroup();
  }

  @Test
  @DisplayName(
      "should throw an exception if right side port is not available while fetching all cards")
  void shouldThrowAnExceptionIfRightSidePortIsNotAvailableWhileFetchingAllCards() {
    CardDomain cardDomain = new CardDomain(null);
    assertThrows(
        GoTinyDomainException.class,
        cardDomain::getCardsNotBelongToGroup,
        CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
  }

  @Test
  @DisplayName("should be able to get cards belongs to group with the support of stub")
  void shouldBeAbleToGetCardsBelongsToGroupWithTheSupportOfStub() {
    Card card = constructCard();
    List<Card> cards = List.of(card);
    lenient().when(obtainCard.getCardsBelongToGroup(GROUP_NAME)).thenReturn(cards);
    CardDomain cardDomain = new CardDomain(obtainCard);
    List<Card> fetchedCards = cardDomain.getCardsBelongToGroup(GROUP_NAME);
    assertThat(fetchedCards.get(0))
        .extracting(
            "title", "description", "name", "actualUrl", "expiresIn", "createdBy", "tinyUrl")
        .contains(
            card.getTitle(),
            card.getDescription(),
            card.getName(),
            card.getActualUrl(),
            card.getExpiresIn(),
            card.getCreatedBy(),
            card.getTinyUrl());
    verify(obtainCard).getCardsBelongToGroup(GROUP_NAME);
  }

  @Test
  @DisplayName(
      "should throw an exception if right side port is not available while fetching cards belong to group")
  void shouldThrowAnExceptionIfRightSidePortIsNotAvailableWhileFetchingCardsBelongToGroup() {
    CardDomain cardDomain = new CardDomain(null);
    assertThrows(
        GoTinyDomainException.class,
        () -> cardDomain.getCardsBelongToGroup(GROUP_NAME),
        CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
  }

  @Test
  @DisplayName("should be able to get cards belongs to group by status with the support of stub")
  void shouldBeAbleToGetCardsBelongsToGroupByStatusWithTheSupportOfStub() {
    Card card = constructCard();
    List<Card> cards = List.of(card);
    lenient()
        .when(obtainCard.getCardsBelongToGroupByStatus(GROUP_NAME, APPROVAL_STATUS))
        .thenReturn(cards);
    CardDomain cardDomain = new CardDomain(obtainCard);
    List<Card> fetchedCards = cardDomain.getCardsBelongToGroupByStatus(GROUP_NAME, APPROVAL_STATUS);
    assertThat(fetchedCards.get(0))
        .extracting(
            "title", "description", "name", "actualUrl", "expiresIn", "createdBy", "tinyUrl")
        .contains(
            card.getTitle(),
            card.getDescription(),
            card.getName(),
            card.getActualUrl(),
            card.getExpiresIn(),
            card.getCreatedBy(),
            card.getTinyUrl());
    verify(obtainCard).getCardsBelongToGroupByStatus(GROUP_NAME, APPROVAL_STATUS);
  }

  @Test
  @DisplayName(
      "should throw an exception if right side port is not available while fetching card belongs to group by status")
  void
      shouldThrowAnExceptionIfRightSidePortIsNotAvailableWhileFetchingCardsBelongToGroupByStatus() {
    CardDomain cardDomain = new CardDomain(null);
    assertThrows(
        GoTinyDomainException.class,
        () -> cardDomain.getCardsBelongToGroupByStatus(GROUP_NAME, APPROVAL_STATUS),
        CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
  }

  @Test
  @DisplayName("should be able to update card in the group with the support of stub")
  void shouldBeAbleToUpdateCardInTheGroupWithTheSupportOfStub() {
    Card card = constructCard();
    lenient().when(obtainCard.updateCardInTheGroup(card, GROUP_NAME)).thenReturn(APPROVAL_STATUS);
    CardDomain cardDomain = new CardDomain(obtainCard);
    String approvalStatus = cardDomain.updateCardInTheGroup(card, GROUP_NAME);
    assertThat(approvalStatus).matches(APPROVAL_STATUS);
    verify(obtainCard).updateCardInTheGroup(card, GROUP_NAME);
  }

  @Test
  @DisplayName(
      "should throw an exception if right side port is not available while updating card in the group")
  void shouldThrowAnExceptionIfRightSidePortIsNotAvailableWhileUpdatingCardInTheGroup() {
    Card card = constructCard();
    CardDomain cardDomain = new CardDomain(null);
    assertThrows(
        GoTinyDomainException.class,
        () -> cardDomain.updateCardInTheGroup(card, GROUP_NAME),
        CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
  }

  @Test
  @DisplayName("should be able to delete card in the group with the support of stub")
  void shouldBeAbleToDeleteCardInTheGroupWithTheSupportOfStub() {
    Card card = constructCard();
    lenient().when(obtainCard.deleteCardInTheGroup(card, GROUP_NAME)).thenReturn(APPROVAL_STATUS);
    CardDomain cardDomain = new CardDomain(obtainCard);
    String approvalStatus = cardDomain.deleteCardInTheGroup(card, GROUP_NAME);
    assertThat(approvalStatus).matches(APPROVAL_STATUS);
    verify(obtainCard).deleteCardInTheGroup(card, GROUP_NAME);
  }

  @Test
  @DisplayName(
      "should throw an exception if right side port is not available while deleting card in the group")
  void shouldThrowAnExceptionIfRightSidePortIsNotAvailableWhileDeletingCardInTheGroup() {
    Card card = constructCard();
    CardDomain cardDomain = new CardDomain(null);
    assertThrows(
        GoTinyDomainException.class,
        () -> cardDomain.deleteCardInTheGroup(card, GROUP_NAME),
        CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
  }

  private Card constructCard() {
    return Card.builder()
        .title("TINY-CARD")
        .description("CARD-DESC")
        .name(CARD_NAME)
        .actualUrl(ACTUAL_URL)
        .expiresIn(10)
        .createdBy("TINY-USER")
        .tinyUrl(TINY_URL)
        .build();
  }
}
