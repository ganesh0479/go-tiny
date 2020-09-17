package com.go.tiny.business.domain;

import com.go.tiny.business.model.CardGroup;
import com.go.tiny.business.port.ObtainCardGroup;
import com.go.tiny.business.port.RequestCardGroup;

import java.util.Optional;

import static com.go.tiny.business.helper.CardGroupHelper.CARD_GROUP_HELPER;

public class CardGroupDomain implements RequestCardGroup {
  private ObtainCardGroup obtainCardGroup;

  public CardGroupDomain(ObtainCardGroup obtainCardGroup) {
    this.obtainCardGroup = obtainCardGroup;
  }

  @Override
  public void add(final CardGroup cardGroup) {
    CARD_GROUP_HELPER.initialize(obtainCardGroup);
    CARD_GROUP_HELPER.add(cardGroup);
  }

  @Override
  public Optional<CardGroup> getActualUrl(final String groupName, final String tinyUrl) {
    CARD_GROUP_HELPER.initialize(obtainCardGroup);
    return CARD_GROUP_HELPER.getActualUrl(groupName, tinyUrl);
  }
}
