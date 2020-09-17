package com.go.tiny.business.helper;

import com.go.tiny.business.exception.GoTinyDomainException;
import com.go.tiny.business.model.CardGroup;
import com.go.tiny.business.port.ObtainCardGroup;

import java.util.Optional;

import static com.go.tiny.business.exception.GoTinyDomainExceptionMessage.CARD_GROUP_RIGHT_SIDE_PORT_UNAVAILABLE;
import static java.util.Objects.isNull;

public enum CardGroupHelper {
  CARD_GROUP_HELPER;
  private ObtainCardGroup obtainCardGroup;

  public void initialize(ObtainCardGroup obtainCardGroup) {
    this.obtainCardGroup = obtainCardGroup;
  }

  public void add(final CardGroup cardGroup) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(CARD_GROUP_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    this.obtainCardGroup.add(cardGroup);
  }

  public Optional<CardGroup> getActualUrl(final String groupName, final String tinyUrl) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(CARD_GROUP_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    return this.obtainCardGroup.getActualUrl(groupName, tinyUrl);
  }

  private Boolean isPortNotAvailable() {
    return isNull(obtainCardGroup);
  }
}
