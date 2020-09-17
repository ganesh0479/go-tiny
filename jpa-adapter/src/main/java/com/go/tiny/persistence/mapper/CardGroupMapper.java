package com.go.tiny.persistence.mapper;

import com.go.tiny.business.model.CardGroup;
import com.go.tiny.persistence.entity.CardGroupEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public enum CardGroupMapper {
  CARD_GROUP_MAPPER;

  public Optional<CardGroupEntity> constructCardGroupEntity(final CardGroup cardGroup) {
    return ofNullable(
        CardGroupEntity.builder()
            .cardName(cardGroup.getCardName())
            .groupName(cardGroup.getGroupName())
            .expiresIn(cardGroup.getExpiresIn())
            .actualUrl(cardGroup.getActualUrl())
            .createdTime(LocalDateTime.now())
            .addedBy(cardGroup.getAddedBy())
            .build());
  }

  public Optional<CardGroup> constructCardGroup(final CardGroupEntity cardGroupEntity) {
    return ofNullable(
        CardGroup.builder()
            .cardName(cardGroupEntity.getCardName())
            .groupName(cardGroupEntity.getGroupName())
            .addedBy(cardGroupEntity.getAddedBy())
            .expiresIn(cardGroupEntity.getExpiresIn())
            .actualUrl(cardGroupEntity.getActualUrl())
            .createdTime(cardGroupEntity.getCreatedTime())
            .build());
  }
}
