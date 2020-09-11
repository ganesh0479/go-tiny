package com.go.tiny.persistence.adapter;

import com.go.tiny.business.model.CardGroup;
import com.go.tiny.business.port.ObtainCardGroup;
import com.go.tiny.persistence.dao.CardGroupDao;
import com.go.tiny.persistence.entity.CardGroupEntity;

import java.util.Optional;

import static com.go.tiny.persistence.mapper.CardGroupMapper.CARD_GROUP_MAPPER;
import static java.util.Objects.isNull;

public class CardGroupAdapter implements ObtainCardGroup {
  private CardGroupDao cardGroupDao;

  public CardGroupAdapter(CardGroupDao cardGroupDao) {
    this.cardGroupDao = cardGroupDao;
  }

  @Override
  public void add(final CardGroup cardGroup) {
    if (isNull(cardGroup)) {
      return;
    }
    Optional<CardGroupEntity> cardGroupEntity =
        CARD_GROUP_MAPPER.constructCardGroupEntity(cardGroup);
    cardGroupEntity.ifPresent(cardGroupEntityToSave -> cardGroupDao.save(cardGroupEntityToSave));
  }
}
