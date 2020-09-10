package com.go.tiny.business.helper;

import com.go.tiny.business.model.CardGroup;
import com.go.tiny.business.port.ObtainCardGroup;

import static java.util.Objects.isNull;

public enum CardGroupHelper {
  CARD_GROUP_HELPER;
  private ObtainCardGroup obtainCardGroup;

  public void initialize(ObtainCardGroup obtainCardGroup) {
    this.obtainCardGroup = obtainCardGroup;
  }

  public void add(final CardGroup cardGroup) {
    if (isPortNotAvailable()) {
      throw new NullPointerException();
    }
    this.obtainCardGroup.add(cardGroup);
  }

  private Boolean isPortNotAvailable() {
    return isNull(obtainCardGroup);
  }
}
