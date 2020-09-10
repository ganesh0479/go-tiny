package com.go.tiny.business.helper;

import com.go.tiny.business.exception.GoTinyDomainException;
import com.go.tiny.business.model.Card;
import com.go.tiny.business.port.ObtainCard;

import java.util.List;

import static com.go.tiny.business.exception.GoTinyDomainExceptionMessage.CARD_RIGHT_SIDE_PORT_UNAVAILABLE;
import static java.util.Objects.isNull;

public enum CardHelper {
  CARD_HELPER;
  private ObtainCard obtainCard;

  public void initialize(ObtainCard obtainCard) {
    this.obtainCard = obtainCard;
  }

  public Card create(final Card card) {
    return null;
  }

  public void update(final Card card) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    obtainCard.update(card);
  }

  public void delete(final String name) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    obtainCard.delete(name);
  }

  public Card get(final String name) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    return obtainCard.get(name);
  }

  public List<Card> getAll() {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    return obtainCard.getAll();
  }

  public List<Card> getCardsBelongToGroup(final String groupName) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    return obtainCard.getCardsBelongToGroup(groupName);
  }

  public List<Card> getCardsBelongToGroupByStatus(final String groupName, final String status) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    return obtainCard.getCardsBelongToGroupByStatus(groupName, status);
  }

  public String updateCardInTheGroup(final Card card, final String groupName) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    return obtainCard.updateCardInTheGroup(card, groupName);
  }

  public String deleteCardInTheGroup(final Card card, final String groupName) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    return obtainCard.deleteCardInTheGroup(card, groupName);
  }

  public String getActualUrl(final String tinyUrl) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(CARD_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    return obtainCard.getActualUrl(tinyUrl);
  }

  private Boolean isPortNotAvailable() {
    return isNull(obtainCard);
  }
}
