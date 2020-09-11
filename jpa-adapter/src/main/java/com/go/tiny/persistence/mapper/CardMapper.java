package com.go.tiny.persistence.mapper;

import com.go.tiny.business.model.Card;
import com.go.tiny.persistence.entity.CardEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.*;

public enum CardMapper {
  CARD_MAPPER;

  public Optional<CardEntity> constructCardEntity(final Card card) {
    return ofNullable(
        CardEntity.builder()
            .name(card.getName())
            .title(card.getTitle())
            .description(card.getDescription())
            .actualUrl(card.getActualUrl())
            .expiresIn(card.getExpiresIn())
            .createdBy(card.getCreatedBy())
            .tinyUrl(card.getTinyUrl())
            .picture(card.getPicture())
            .build());
  }

  public Optional<Card> constructCard(final CardEntity cardEntity) {
    return ofNullable(
        Card.builder()
            .name(cardEntity.getName())
            .title(cardEntity.getTitle())
            .description(cardEntity.getDescription())
            .actualUrl(cardEntity.getActualUrl())
            .expiresIn(cardEntity.getExpiresIn())
            .createdBy(cardEntity.getCreatedBy())
            .tinyUrl(cardEntity.getTinyUrl())
            .picture(cardEntity.getPicture())
            .build());
  }

  public Optional<CardEntity> constructCardEntityToUpdate(final Card card, CardEntity cardEntity) {
    if (isNull(cardEntity)) {
      return empty();
    }
    if (nonNull(card.getTitle())) {
      cardEntity = cardEntity.toBuilder().title(card.getTitle()).build();
    }
    if (nonNull(card.getDescription())) {
      cardEntity = cardEntity.toBuilder().description(card.getDescription()).build();
    }
    if (nonNull(card.getActualUrl())) {
      cardEntity = cardEntity.toBuilder().actualUrl(card.getActualUrl()).build();
    }
    if (nonNull(card.getPicture())) {
      cardEntity = cardEntity.toBuilder().picture(card.getPicture()).build();
    }
    return of(cardEntity);
  }

  public List<Card> constructCards(final List<CardEntity> cardEntities) {
    return cardEntities.isEmpty()
        ? emptyList()
        : cardEntities.stream()
            .filter(Objects::nonNull)
            .map(this::constructCard)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
  }
}
