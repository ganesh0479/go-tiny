package com.go.tiny.persistence.adapter;

import com.go.tiny.business.model.Card;
import com.go.tiny.business.model.CardGroup;
import com.go.tiny.business.port.ObtainCard;
import com.go.tiny.persistence.dao.CardDao;
import com.go.tiny.persistence.dao.CardGroupDao;
import com.go.tiny.persistence.dao.SequenceDao;
import com.go.tiny.persistence.entity.CardEntity;
import com.go.tiny.persistence.entity.CardGroupEntity;
import com.go.tiny.persistence.entity.SequenceEntity;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.go.tiny.persistence.constant.GoTinyJpaConstant.*;
import static com.go.tiny.persistence.mapper.CardGroupMapper.CARD_GROUP_MAPPER;
import static com.go.tiny.persistence.mapper.CardMapper.CARD_MAPPER;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Objects.isNull;
import static java.util.Optional.empty;

public class CardAdapter implements ObtainCard {
  private CardDao cardDao;
  private CardGroupDao cardGroupDao;
  private SequenceDao sequenceDao;

  public CardAdapter(CardDao cardDao, CardGroupDao cardGroupDao, SequenceDao sequenceDao) {
    this.cardDao = cardDao;
    this.cardGroupDao = cardGroupDao;
    this.sequenceDao = sequenceDao;
  }

  @Override
  public Optional<Card> create(final Card card) {
    if (isNull(card)) {
      return empty();
    }
    Optional<CardEntity> cardEntity = CARD_MAPPER.constructCardEntity(card);
    return cardEntity
        .map(
            cardEntityToSave -> {
              CardEntity responseCardEntity = cardDao.save(cardEntityToSave);
              CARD_GROUP_MAPPER
                  .constructCardGroupEntity(
                      CardGroup.builder()
                          .cardName(responseCardEntity.getName())
                          .groupName(TINY)
                          .expiresIn(responseCardEntity.getExpiresIn())
                          .build())
                  .ifPresent(cardGroupEntity -> cardGroupDao.save(cardGroupEntity));
              return CARD_MAPPER.constructCard(responseCardEntity);
            })
        .orElse(empty());
  }

  @Override
  public void update(final Card card) {
    if (isNull(card)) {
      return;
    }
    Optional<CardEntity> cardEntity = cardDao.getByName(card.getName());
    Optional<CardEntity> cardEntityToUpdate =
        CARD_MAPPER.constructCardEntityToUpdate(card, cardEntity.orElse(null));
    cardEntityToUpdate.ifPresent(cardEntityToSave -> cardDao.save(cardEntityToSave));
  }

  @Override
  @Transactional
  public void delete(final String name) {
    if (isNull(name)) {
      return;
    }
    cardDao.deleteByName(name);
  }

  @Override
  public Optional<Card> get(final String name) {
    if (isNull(name)) {
      return empty();
    }
    Optional<CardEntity> cardEntity = cardDao.getByName(name);
    return cardEntity.flatMap(CARD_MAPPER::constructCard);
  }

  @Override
  public List<Card> getCardsNotBelongToGroup() {
    List<CardEntity> cardEntities =
        StreamSupport.stream(cardDao.findAll().spliterator(), false).collect(Collectors.toList());
    List<CardGroupEntity> cardGroupEntityList =
        StreamSupport.stream(cardGroupDao.findAll().spliterator(), false)
            .map(this::filterExpiredCards)
            .collect(Collectors.toList());
    List<String> cardGroupEntities =
        cardGroupEntityList.stream().map(CardGroupEntity::getCardName).collect(Collectors.toList());
    List<CardEntity> filteredCards =
        cardEntities.stream()
            .filter(cardEntity -> !cardGroupEntities.contains(cardEntity.getName()))
            .collect(Collectors.toList());
    return CARD_MAPPER.constructCards(filteredCards);
  }

  @Override
  public List<Card> getCardsBelongToGroup(final String groupName) {
    if (isNull(groupName)) {
      return emptyList();
    }
    List<CardGroupEntity> cardGroupEntities = cardGroupDao.getByGroupName(groupName);
    List<String> cardNames =
        cardGroupEntities.stream()
            .filter(cardGroupEntity -> !UPDATE_PENDING.equals(cardGroupEntity.getStatus()))
            .filter(cardGroupEntity -> !DELETE_PENDING.equals(cardGroupEntity.getStatus()))
            .filter(cardGroupEntity -> !APPROVED.equals(cardGroupEntity.getStatus()))
            .map(CardGroupEntity::getCardName)
            .collect(Collectors.toList());
    List<CardEntity> cardEntities = cardDao.getAllByNameIn(cardNames);
    return CARD_MAPPER.constructCards(cardEntities);
  }

  @Override
  public List<Card> getCardsBelongToGroupByStatus(final String groupName, final String status) {
    if (isNull(groupName) || isNull(status)) {
      return emptyList();
    }
    List<CardGroupEntity> cardGroupEntities =
        cardGroupDao.getByGroupNameAndStatus(groupName, status);
    List<String> cardNames =
        cardGroupEntities.stream().map(CardGroupEntity::getCardName).collect(Collectors.toList());
    List<CardEntity> cardEntities = cardDao.getAllByNameIn(cardNames);
    return CARD_MAPPER.constructCards(cardEntities);
  }

  @Override
  public String updateCardInTheGroup(final Card card, final String groupName) {
    if (isNull(card) || isNull(card.getName()) || isNull(groupName)) {
      return null;
    }
    Optional<CardGroupEntity> cardGroupEntity =
        cardGroupDao.getByGroupNameAndCardName(groupName, card.getName());
    return cardGroupEntity
        .map(
            cardGroupEntityToSave -> {
              Optional<CardEntity> cardEntity =
                  cardDao.getByName(cardGroupEntityToSave.getCardName());
              Optional<CardEntity> cardEntityToUpdate =
                  CARD_MAPPER.constructCardEntityToUpdate(card, cardEntity.orElse(null));
              cardEntityToUpdate.ifPresent(cardEntityToSave -> cardDao.save(cardEntityToSave));
              CardGroupEntity cardGroupEntityToUpdate = cardGroupEntity.get();
              cardGroupEntityToUpdate.setStatus(UPDATE_PENDING);
              cardGroupDao.save(cardGroupEntityToUpdate);
              return UPDATE_PENDING;
            })
        .orElse(null);
  }

  @Override
  public String deleteCardInTheGroup(final Card card, final String groupName) {
    if (isNull(card) || isNull(card.getName()) || isNull(groupName)) {
      return null;
    }
    Optional<CardGroupEntity> cardGroupEntity =
        cardGroupDao.getByGroupNameAndCardName(groupName, card.getName());
    return cardGroupEntity
        .map(
            cardGroupEntityToSave -> {
              CardGroupEntity cardGroupEntityToUpdate = cardGroupEntity.get();
              cardGroupEntityToUpdate.setStatus(DELETE_PENDING);
              cardGroupDao.save(cardGroupEntityToUpdate);
              return DELETE_PENDING;
            })
        .orElse(null);
  }

  @Override
  public long getUniqueId() {
    return sequenceDao.save(SequenceEntity.builder().build()).getId();
  }

  @Override
  public String uploadAvatar(final byte[] fileData, final String cardName) {
    if (isNull(fileData) || isNull(cardName)) {
      return "Unable to upload image";
    }
    Optional<CardEntity> cardEntity = cardDao.getByName(cardName);
    cardEntity.ifPresent(
        cardEntityToUpdate ->
            cardDao.save(cardEntityToUpdate.toBuilder().picture(fileData).build()));
    return AVATAR_UPLOADED;
  }

  @Override
  public Set<String> getAvailableShortURLsByFilteringExistingUrls(
      final String groupName, final Set<String> generatedShortUrls) {
    return this.cardGroupDao.getByGroupNameAndTinyUrlNotIn(groupName, generatedShortUrls).stream()
        .map(CardGroupEntity::getTinyUrl)
        .collect(Collectors.toSet());
  }

  @Override
  public Set<String> getAvailableShortUrls(final String groupName, final String tinyUrl) {
    if (isNull(groupName) || isNull(tinyUrl)) {
      return emptySet();
    }
    return this.cardGroupDao.getByGroupNameAndTinyUrl(groupName, tinyUrl).stream()
        .map(CardGroupEntity::getTinyUrl)
        .collect(Collectors.toSet());
  }

  @Override
  public Boolean checkTinyUrlAlreadyExist(final String groupName, final String tinyUrl) {
    if (isNull(groupName) || isNull(tinyUrl)) {
      return false;
    }
    return this.cardGroupDao.existsByGroupNameAndTinyUrl(groupName, tinyUrl);
  }

  private CardGroupEntity filterExpiredCards(final CardGroupEntity cardGroupEntity) {
    LocalDateTime createdOn = cardGroupEntity.getCreatedTime();
    LocalDateTime currentTime = LocalDateTime.now();
    return currentTime.isBefore(createdOn.plusMinutes(cardGroupEntity.getExpiresIn()))
        ? cardGroupEntity
        : null;
  }
}
