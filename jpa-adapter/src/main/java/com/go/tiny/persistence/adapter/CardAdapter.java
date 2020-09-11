package com.go.tiny.persistence.adapter;

import com.go.tiny.business.model.Card;
import com.go.tiny.business.port.ObtainCard;
import com.go.tiny.persistence.dao.CardDao;
import com.go.tiny.persistence.dao.CardGroupDao;
import com.go.tiny.persistence.entity.CardEntity;
import com.go.tiny.persistence.entity.CardGroupEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.go.tiny.persistence.constant.GoTinyJpaConstant.UPDATE_PENDING;
import static com.go.tiny.persistence.constant.GoTinyJpaConstant.DELETE_PENDING;
import static com.go.tiny.persistence.constant.GoTinyJpaConstant.SEQUENCE_NAME;

import static com.go.tiny.persistence.mapper.CardMapper.CARD_MAPPER;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.Optional.empty;

public class CardAdapter implements ObtainCard {
  private CardDao cardDao;
  private CardGroupDao cardGroupDao;
  @Autowired private EntityManager entityManager;

  public CardAdapter(CardDao cardDao, CardGroupDao cardGroupDao) {
    this.cardDao = cardDao;
    this.cardGroupDao = cardGroupDao;
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
  public List<Card> getAll() {
    Iterable<CardEntity> cardEntities = cardDao.findAll();
    return CARD_MAPPER.constructCards(
        StreamSupport.stream(cardEntities.spliterator(), false).collect(Collectors.toList()));
  }

  @Override
  public List<Card> getCardsBelongToGroup(final String groupName) {
    if (isNull(groupName)) {
      return emptyList();
    }
    List<CardGroupEntity> cardGroupEntities = cardGroupDao.getByGroupName(groupName);
    List<String> cardNames =
        cardGroupEntities.stream().map(CardGroupEntity::getCardName).collect(Collectors.toList());
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
  public String getActualUrl(final String tinyUrl) {
    if (isNull(tinyUrl)) {
      return null;
    }
    Optional<CardEntity> cardEntity = cardDao.getByTinyUrl(tinyUrl);
    return cardEntity.map(CardEntity::getActualUrl).orElse(null);
  }

  @Override
  public long getUniqueId() {
    return entityManager.createNativeQuery(SEQUENCE_NAME).executeUpdate();
  }
}
