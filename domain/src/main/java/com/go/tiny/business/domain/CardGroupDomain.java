package com.go.tiny.business.domain;

import com.go.tiny.business.model.CardGroup;
import com.go.tiny.business.port.ObtainCardGroup;

import static com.go.tiny.business.helper.CardGroupHelper.CARD_GROUP_HELPER;

public class CardGroupDomain {
  private ObtainCardGroup obtainCardGroup;

  public CardGroupDomain(ObtainCardGroup obtainCardGroup) {
    this.obtainCardGroup = obtainCardGroup;
  }

  public void add(final CardGroup cardGroup) {
    CARD_GROUP_HELPER.initialize(obtainCardGroup);
    CARD_GROUP_HELPER.add(cardGroup);
  }
}
