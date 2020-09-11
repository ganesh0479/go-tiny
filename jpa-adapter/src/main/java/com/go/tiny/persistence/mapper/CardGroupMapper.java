package com.go.tiny.persistence.mapper;

import com.go.tiny.business.model.CardGroup;
import com.go.tiny.persistence.entity.CardGroupEntity;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public enum CardGroupMapper {
  CARD_GROUP_MAPPER;

  public Optional<CardGroupEntity> constructCardGroupEntity(final CardGroup cardGroup) {
    return ofNullable(
        CardGroupEntity.builder()
            .cardName(cardGroup.getCardName())
            .groupName(cardGroup.getGroupName())
            .addedBy(cardGroup.getAddedBy())
            .build());
  }
}
