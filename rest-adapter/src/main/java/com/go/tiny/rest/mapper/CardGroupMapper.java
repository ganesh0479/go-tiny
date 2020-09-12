package com.go.tiny.rest.mapper;

import com.go.tiny.business.model.CardGroup;
import com.go.tiny.rest.model.CardGroupRequest;

public enum CardGroupMapper {
  CARD_GROUP_MAPPER;

  public CardGroup constructCardGroup(final CardGroupRequest cardGroupRequest) {
    return CardGroup.builder()
        .cardName(cardGroupRequest.getCardName())
        .groupName(cardGroupRequest.getGroupName())
        .addedBy(cardGroupRequest.getAddedBy())
        .build();
  }
}
