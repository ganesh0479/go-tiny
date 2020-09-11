package com.go.tiny.persistence;

import com.go.tiny.business.model.Group;
import com.go.tiny.business.port.ObtainGroup;
import com.go.tiny.persistence.dao.CardDao;
import com.go.tiny.persistence.dao.CardGroupDao;
import com.go.tiny.persistence.dao.GroupDao;
import com.go.tiny.persistence.entity.CardEntity;
import com.go.tiny.persistence.entity.CardGroupEntity;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.go.tiny.persistence.constant.GoTinyJpaConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = GoTinyJpaTestConfiguration.class)
public class GroupAdapterTest {
  @Autowired private GroupDao groupDao;
  @Autowired private CardGroupDao cardGroupDao;
  @Autowired private CardDao cardDao;
  @Autowired private ObtainGroup obtainGroup;

  @Test
  @DisplayName("should be able to create group with the support of database")
  public void shouldBeAbleToCreateGroupWithTheSupportOfDatabase() {
    // Given
    Group group = constructGroup();
    // When
    obtainGroup.create(group);
    // Then
    assertThat(groupDao.findAll()).isNotEmpty();
  }

  @Test
  @DisplayName("should not be able to create unknown group with the support of database")
  public void shouldNotBeAbleToCreateUnknownGroupWithTheSupportOfDatabase() {
    // When
    obtainGroup.create(null);
    // Then
    assertThat(groupDao.findAll()).isEmpty();
  }

  @Test
  @DisplayName("should be able to authorize card to display in group with the support of database")
  public void shouldBeAbleToAuthorizeCardToDisplayInGroupWithTheSupportOfDatabase() {
    // Given
    Group group = constructGroup();
    CardGroupEntity cardGroupEntity = constructCardGroup(group.getName());
    cardGroupDao.save(cardGroupEntity);
    // When
    obtainGroup.authorizeCardToDisplayInGroup(
        cardGroupEntity.getGroupName(), cardGroupEntity.getCardName());
    // Then
    Optional<CardGroupEntity> authorizedCardGroup =
        cardGroupDao.getByGroupNameAndCardName(
            cardGroupEntity.getGroupName(), cardGroupEntity.getCardName());
    assertThat(authorizedCardGroup).isPresent();
    assertThat(authorizedCardGroup.get().getStatus()).matches(AUTHORIZED);
  }

  @Test
  @DisplayName(
      "should not be able to authorize unknown card to display in group with the support of database")
  public void shouldNotBeAbleToAuthorizeUnknownCardToDisplayInGroupWithTheSupportOfDatabase() {
    // Given
    Group group = constructGroup();
    // When
    obtainGroup.authorizeCardToDisplayInGroup(group.getName(), null);
    // Then
    assertThat(cardGroupDao.findAll()).isEmpty();
  }

  @Test
  @DisplayName(
      "should not be able to authorize card to display in unknown group with the support of database")
  public void shouldNotBeAbleToAuthorizeCardToDisplayInUnknownGroupWithTheSupportOfDatabase() {
    // When
    obtainGroup.authorizeCardToDisplayInGroup(null, null);
    // Then
    assertThat(cardGroupDao.findAll()).isEmpty();
  }

  //
  @Test
  @DisplayName(
      "should be able to approve pending card to display in group with the support of database")
  public void shouldBeAbleToApprovePendingCardToDisplayInGroupWithTheSupportOfDatabase() {
    // Given
    Group group = constructGroup();
    CardGroupEntity cardGroupEntity = constructCardGroup(group.getName());
    cardGroupEntity.setStatus(UPDATE_PENDING);
    cardGroupDao.save(cardGroupEntity);
    // When
    obtainGroup.approveCardChangesInTheGroup(
        cardGroupEntity.getGroupName(), cardGroupEntity.getCardName());
    // Then
    Optional<CardGroupEntity> authorizedCardGroup =
        cardGroupDao.getByGroupNameAndCardName(
            cardGroupEntity.getGroupName(), cardGroupEntity.getCardName());
    assertThat(authorizedCardGroup).isPresent();
    assertThat(authorizedCardGroup.get().getStatus()).matches(APPROVED);
  }

  @Test
  @DisplayName(
      "should be able to delete card on delete pending status to display in group with the support of database")
  public void
      shouldBeAbleToDeleteCardOnDeletePendingStatusToDisplayInGroupWithTheSupportOfDatabase() {
    // Given
    Group group = constructGroup();
    CardGroupEntity cardGroupEntity = constructCardGroup(group.getName());
    cardGroupEntity.setStatus(DELETE_PENDING);
    cardGroupDao.save(cardGroupEntity);
    // When
    obtainGroup.approveCardChangesInTheGroup(
        cardGroupEntity.getGroupName(), cardGroupEntity.getCardName());
    // Then
    Optional<CardEntity> cardEntity = cardDao.getByName(cardGroupEntity.getCardName());
    assertThat(cardEntity).isNotPresent();
  }

  @Test
  @DisplayName(
      "should not be able to approve unknown card to display in group with the support of database")
  public void shouldNotBeAbleToApproveUnknownCardToDisplayInGroupWithTheSupportOfDatabase() {
    // Given
    Group group = constructGroup();
    // When
    obtainGroup.approveCardChangesInTheGroup(group.getName(), null);
    // Then
    assertThat(cardGroupDao.findAll()).isEmpty();
  }

  @Test
  @DisplayName(
      "should not be able to authorize card to display in unknown group with the support of database")
  public void shouldNotBeAbleToApproveCardToDisplayInUnknownGroupWithTheSupportOfDatabase() {
    // When
    obtainGroup.approveCardChangesInTheGroup(null, null);
    // Then
    assertThat(cardGroupDao.findAll()).isEmpty();
  }

  private Group constructGroup() {
    return Group.builder().name("TINY-GROUP").createdBy("TINY-USER").build();
  }

  private CardGroupEntity constructCardGroup(final String groupName) {
    return CardGroupEntity.builder()
        .cardName("TINY-CARD")
        .groupName(groupName)
        .addedBy("TINY-USER")
        .build();
  }
}
