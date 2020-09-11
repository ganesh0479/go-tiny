package com.go.tiny.persistence;

import com.go.tiny.business.model.Card;
import com.go.tiny.business.port.ObtainCard;
import com.go.tiny.persistence.dao.CardDao;
import com.go.tiny.persistence.dao.CardGroupDao;
import com.go.tiny.persistence.entity.CardEntity;
import com.go.tiny.persistence.entity.CardGroupEntity;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static com.go.tiny.persistence.constant.GoTinyJpaConstant.DELETE_PENDING;
import static com.go.tiny.persistence.constant.GoTinyJpaConstant.UPDATE_PENDING;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = GoTinyJpaTestConfiguration.class)
public class CardJpaAdapterTest {
  @Autowired private CardDao cardDao;
  @Autowired private CardGroupDao cardGroupDao;

  @Autowired
  @Qualifier("test-card-adapter")
  private ObtainCard obtainCard;

  @Test
  @DisplayName("should be able to create card with the support of database")
  public void shouldBeAbleToCreateCardWithTheSupportOfDatabase() {
    // Given
    Card card = constructCard();
    // When
    Optional<Card> createdCard = obtainCard.create(card);
    // Then
    assertThat(createdCard).isPresent();
  }

  @Test
  @DisplayName("should give empty response if card is not available with the support of database")
  public void shouldGiveEmptyResponseIfCardIsNotAvailableWithTheSupportOfDatabase() {
    // When
    Optional<Card> createdCard = obtainCard.create(null);
    // Then
    assertThat(createdCard).isNotPresent();
  }

  @Test
  @DisplayName("should be able to update card with the support of database")
  public void shouldBeAbleToUpdateCardWithTheSupportOfDatabase() {
    // Given
    CardEntity cardEntity = constructCardEntity();
    cardDao.save(cardEntity);
    Card card =
        Card.builder()
            .name(cardEntity.getName())
            .title("Card-Title")
            .description("card-description")
            .actualUrl("card-actual-url")
            .picture("sample".getBytes())
            .build();
    // When
    obtainCard.update(card);
    // Then
    Optional<Card> updatedCard = obtainCard.get(cardEntity.getName());
    assertThat(updatedCard).isPresent();
  }

  @Test
  @DisplayName(
      "should not be able to update card with unknown field update with the support of database")
  public void shouldNotBeAbleToUpdateCardWithUnknownFieldUpdateWithTheSupportOfDatabase() {
    // Given
    CardEntity cardEntity = constructCardEntity();
    cardDao.save(cardEntity);
    Card card = Card.builder().name(cardEntity.getName()).tinyUrl("sample").build();
    // When
    obtainCard.update(card);
    // Then
    Optional<Card> updatedCard = obtainCard.get(cardEntity.getName());
    assertThat(updatedCard).isPresent();
  }

  @Test
  @DisplayName("should not be able to update unknown card with the support of database")
  public void shouldNotBeAbleToUpdateUnknownCardWithTheSupportOfDatabase() {
    // Given
    CardEntity cardEntity = constructCardEntity();
    Card card = Card.builder().name(cardEntity.getName()).tinyUrl("sample").build();
    // When
    obtainCard.update(card);
    // Then
    Optional<Card> updatedCard = obtainCard.get(cardEntity.getName());
    assertThat(updatedCard).isNotPresent();
  }

  @Test
  @DisplayName(
      "should not give response if card is not available on updating with the support of database")
  public void shouldNotGiveResponseIfCardIsNotAvailableOnUpdatingWithTheSupportOfDatabase() {
    // When
    obtainCard.update(null);
    // Then
    assertThat(true);
  }

  @Test
  @DisplayName("should be able to delete card with the support of database")
  public void shouldBeAbleToDeleteCardWithTheSupportOfDatabase() {
    // Given
    CardEntity cardEntity = constructCardEntity();
    cardDao.save(cardEntity);
    // When
    obtainCard.delete(cardEntity.getName());
    // Then
    Optional<Card> deletedCard = obtainCard.get(cardEntity.getName());
    assertThat(deletedCard).isNotPresent();
  }

  @Test
  @DisplayName("should not give response on deleting unknown card with the support of database")
  public void shouldNotGiveResponseOnDeletingUnknownCardWithTheSupportOfDatabase() {
    // When
    obtainCard.delete(null);
    // Then
    assertThat(true);
  }

  @Test
  @DisplayName("should not give response on fetching unknown card with the support of database")
  public void shouldNotGiveResponseOnFetchingUnknownCardWithTheSupportOfDatabase() {
    // When
    obtainCard.get(null);
    // Then
    assertThat(true);
  }

  @Test
  @DisplayName("should be able to get all the cards with the support of database")
  public void shouldBeAbleToGetAllTheCardsWithTheSupportOfDatabase() {
    // Given
    CardEntity cardEntity = constructCardEntity();
    cardDao.save(cardEntity);
    // When
    List<Card> cards = obtainCard.getAll();
    // Then
    assertThat(cards).isNotEmpty();
  }

  @Test
  @DisplayName("should be able to get zero cards with the support of database")
  public void shouldBeAbleToGetZeroCardsWithTheSupportOfDatabase() {
    // When
    List<Card> cards = obtainCard.getAll();
    // Then
    assertThat(cards).isEmpty();
  }

  @Test
  @DisplayName("should be able to get cards belong to group with the support of database")
  public void shouldBeAbleToGetCardsBelongToGroupWithTheSupportOfDatabase() {
    // Given
    String groupName = "GROUP-NAME";
    CardEntity cardEntity = constructCardEntity();
    cardDao.save(cardEntity);
    CardGroupEntity cardGroupEntity =
        constructCardGroupEntity(cardEntity.getName(), groupName, "UPDATE_PENDING");
    cardGroupDao.save(cardGroupEntity);
    // When
    List<Card> cards = obtainCard.getCardsBelongToGroup(groupName);
    // Then
    assertThat(cards).isNotEmpty();
  }

  @Test
  @DisplayName("should not be able to get cards belong to group with the support of database")
  public void shouldNotBeAbleToGetCardsBelongToGroupWithTheSupportOfDatabase() {
    // Given
    String groupName = null;
    CardEntity cardEntity = constructCardEntity();
    cardDao.save(cardEntity);
    CardGroupEntity cardGroupEntity =
        constructCardGroupEntity(cardEntity.getName(), groupName, "UPDATE_PENDING");
    cardGroupDao.save(cardGroupEntity);
    // When
    List<Card> cards = obtainCard.getCardsBelongToGroup(groupName);
    // Then
    assertThat(cards).isEmpty();
  }

  @Test
  @DisplayName("should be able to get cards belong to group by status with the support of database")
  public void shouldBeAbleToGetCardsBelongToGroupByStatusWithTheSupportOfDatabase() {
    // Given
    String groupName = "GROUP-NAME";
    String status = "UPDATE_PENDING";
    CardEntity cardEntity = constructCardEntity();
    cardDao.save(cardEntity);
    CardGroupEntity cardGroupEntity =
        constructCardGroupEntity(cardEntity.getName(), groupName, status);
    cardGroupDao.save(cardGroupEntity);
    // When
    List<Card> cards = obtainCard.getCardsBelongToGroupByStatus(groupName, status);
    // Then
    assertThat(cards).isNotEmpty();
  }

  @Test
  @DisplayName(
      "should not be able to get cards belong to unknown group by status with the support of database")
  public void shouldNotBeAbleToGetCardsBelongToUnknownGroupByStatusWithTheSupportOfDatabase() {
    // Given
    String groupName = null;
    String status = "UPDATE_PENDING";
    CardEntity cardEntity = constructCardEntity();
    cardDao.save(cardEntity);
    CardGroupEntity cardGroupEntity =
        constructCardGroupEntity(cardEntity.getName(), groupName, status);
    cardGroupDao.save(cardGroupEntity);
    // When
    List<Card> cards = obtainCard.getCardsBelongToGroupByStatus(groupName, status);
    // Then
    assertThat(cards).isEmpty();
  }

  @Test
  @DisplayName(
      "should not be able to get cards belong to group by unknown status with the support of database")
  public void shouldNotBeAbleToGetCardsBelongToGroupUnknownStatusWithTheSupportOfDatabase() {
    // Given
    String groupName = null;
    String status = "UPDATE_PENDING";
    CardEntity cardEntity = constructCardEntity();
    cardDao.save(cardEntity);
    CardGroupEntity cardGroupEntity =
        constructCardGroupEntity(cardEntity.getName(), groupName, status);
    cardGroupDao.save(cardGroupEntity);
    // When
    List<Card> cards = obtainCard.getCardsBelongToGroupByStatus(groupName, status);
    // Then
    assertThat(cards).isEmpty();
  }

  @Test
  @DisplayName("should be able to update card in the group with the support of database")
  public void shouldBeAbleToUpdateCardInTheGroupWithTheSupportOfDatabase() {
    // Given
    String groupName = "GROUP-NAME";
    String status = "UPDATE_PENDING";

    CardEntity cardEntity = constructCardEntity();
    cardDao.save(cardEntity);
    Card card =
        Card.builder()
            .name(cardEntity.getName())
            .title("Card-Title")
            .description("card-description")
            .actualUrl("card-actual-url")
            .picture("sample".getBytes())
            .build();
    CardGroupEntity cardGroupEntity = constructCardGroupEntity(card.getName(), groupName, status);
    cardGroupDao.save(cardGroupEntity);
    // When
    String approvalStatus = obtainCard.updateCardInTheGroup(card, groupName);
    // Then
    assertThat(approvalStatus).matches(UPDATE_PENDING);
  }

  @Test
  @DisplayName(
      "should be able to update card in the group when card is unavailable with the support of database")
  public void shouldNotBeAbleToUpdateCardInTheGroupWhenCardIsUnavailableWithTheSupportOfDatabase() {
    // Given
    String groupName = "TINY-GROUP";
    // When
    String approvalStatus = obtainCard.updateCardInTheGroup(null, groupName);
    // Then
    assertThat(approvalStatus).isNull();
  }

  @Test
  @DisplayName(
      "should be able to update card in the group when card name is unavailable with the support of database")
  public void
      shouldNotBeAbleToUpdateCardInTheGroupWhenCardNameIsUnavailableWithTheSupportOfDatabase() {
    // Given
    String groupName = "TINY-GROUP";
    Card card = Card.builder().build();
    // When
    String approvalStatus = obtainCard.updateCardInTheGroup(card, groupName);
    // Then
    assertThat(approvalStatus).isNull();
  }

  @Test
  @DisplayName(
      "should be able to update card in the group when group name is unavailable with the support of database")
  public void
      shouldNotBeAbleToUpdateCardInTheGroupWhenGroupNameIsUnavailableWithTheSupportOfDatabase() {
    // Given
    String groupName = null;
    Card card = Card.builder().name("TINY-CARD").build();
    // When
    String approvalStatus = obtainCard.updateCardInTheGroup(card, groupName);
    // Then
    assertThat(approvalStatus).isNull();
  }

  @Test
  @DisplayName("should be able to delete card in the group with the support of database")
  public void shouldBeAbleToDeleteCardInTheGroupWithTheSupportOfDatabase() {
    // Given
    String groupName = "GROUP-NAME";
    String status = "DELETE_PENDING";
    CardEntity cardEntity = constructCardEntity();
    cardDao.save(cardEntity);
    Card card =
        Card.builder()
            .name(cardEntity.getName())
            .title("Card-Title")
            .description("card-description")
            .actualUrl("card-actual-url")
            .picture("sample".getBytes())
            .build();
    CardGroupEntity cardGroupEntity = constructCardGroupEntity(card.getName(), groupName, status);
    cardGroupDao.save(cardGroupEntity);
    // When
    String approvalStatus = obtainCard.deleteCardInTheGroup(card, groupName);
    // Then
    assertThat(approvalStatus).matches(DELETE_PENDING);
  }

  @Test
  @DisplayName(
      "should be able to delete card in the group when card is unavailable with the support of database")
  public void shouldNotBeAbleToDeleteCardInTheGroupWhenCardIsUnavailableWithTheSupportOfDatabase() {
    // Given
    String groupName = "TINY-GROUP";
    // When
    String approvalStatus = obtainCard.deleteCardInTheGroup(null, groupName);
    // Then
    assertThat(approvalStatus).isNull();
  }

  @Test
  @DisplayName(
      "should be able to delete card in the group when card name is unavailable with the support of database")
  public void
      shouldNotBeAbleToDeleteCardInTheGroupWhenCardNameIsUnavailableWithTheSupportOfDatabase() {
    // Given
    String groupName = "TINY-GROUP";
    Card card = Card.builder().build();
    // When
    String approvalStatus = obtainCard.deleteCardInTheGroup(card, groupName);
    // Then
    assertThat(approvalStatus).isNull();
  }

  @Test
  @DisplayName(
      "should be able to delete card in the group when group name is unavailable with the support of database")
  public void
      shouldNotBeAbleToDeleteCardInTheGroupWhenGroupNameIsUnavailableWithTheSupportOfDatabase() {
    // Given
    String groupName = null;
    Card card = Card.builder().name("TINY-CARD").build();
    // When
    String approvalStatus = obtainCard.deleteCardInTheGroup(card, groupName);
    // Then
    assertThat(approvalStatus).isNull();
  }

  @Test
  @DisplayName("should be able to get actual url with the support of database")
  public void shouldBeAbleToGetActualUrlWithTheSupportOfDatabase() {
    // Given
    CardEntity cardEntity = constructCardEntity();
    cardDao.save(cardEntity);
    // When
    String actualUrl = obtainCard.getActualUrl(cardEntity.getTinyUrl());
    // Then
    assertThat(actualUrl).matches(cardEntity.getActualUrl());
  }

  @Test
  @DisplayName("should not get actual url on unknown tiny url with the support of database")
  public void shouldNotGetActualUrlOnUnknownTinyUrlWithTheSupportOfDatabase() {
    // When
    String actualUrl = obtainCard.getActualUrl(null);
    // Then
    assertThat(actualUrl).isNull();
  }

  private CardEntity constructCardEntity() {
    return CardEntity.builder()
        .name("TINY")
        .title("TINY-TITLE")
        .description("TINY-DESCRIPTION")
        .actualUrl("http://random.com/sample//tiny-long-url")
        .expiresIn(30)
        .createdBy("TINY-USER")
        .tinyUrl("http://go-tiny/jhhjsj")
        .picture("picture".getBytes())
        .build();
  }

  private Card constructCard() {
    return Card.builder()
        .name("TINY")
        .title("TINY-TITLE")
        .description("TINY-DESCRIPTION")
        .actualUrl("http://random.com/sample//tiny-long-url")
        .expiresIn(30)
        .createdBy("TINY-USER")
        .tinyUrl("http://go-tiny/jhhjsj")
        .picture("picture".getBytes())
        .build();
  }

  private CardGroupEntity constructCardGroupEntity(
      final String cardName, final String groupName, final String status) {
    return CardGroupEntity.builder()
        .cardName(cardName)
        .groupName(groupName)
        .status(status)
        .addedBy("TINY-USER")
        .build();
  }
}
