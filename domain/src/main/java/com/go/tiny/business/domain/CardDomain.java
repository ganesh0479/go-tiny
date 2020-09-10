package com.go.tiny.business.domain;

import com.go.tiny.business.model.Card;
import com.go.tiny.business.port.ObtainCard;

import java.util.List;

import static com.go.tiny.business.helper.CardHelper.CARD_HELPER;

public class CardDomain {
  private ObtainCard obtainCard;

  public CardDomain(ObtainCard obtainCard) {
    this.obtainCard = obtainCard;
  }

  public Card create(final Card card) {
    CARD_HELPER.initialize(obtainCard);
    return CARD_HELPER.create(card);
  }

  public void update(final Card card) {
    CARD_HELPER.initialize(obtainCard);
    CARD_HELPER.update(card);
  }

  public void delete(final String name) {
    CARD_HELPER.initialize(obtainCard);
    CARD_HELPER.delete(name);
  }

  public Card get(final String name) {
    CARD_HELPER.initialize(obtainCard);
    return CARD_HELPER.get(name);
  }

  public List<Card> getAll() {
    CARD_HELPER.initialize(obtainCard);
    return CARD_HELPER.getAll();
  }

  public List<Card> getCardsBelongToGroup(final String groupName) {
    CARD_HELPER.initialize(obtainCard);
    return CARD_HELPER.getCardsBelongToGroup(groupName);
  }

  public List<Card> getCardsBelongToGroupByStatus(final String groupName, final String status) {
    CARD_HELPER.initialize(obtainCard);
    return CARD_HELPER.getCardsBelongToGroupByStatus(groupName, status);
  }

  public String updateCardInTheGroup(final Card card, final String groupName) {
    CARD_HELPER.initialize(obtainCard);
    return CARD_HELPER.updateCardInTheGroup(card, groupName);
  }

  public String deleteCardInTheGroup(final Card card, final String groupName) {
    CARD_HELPER.initialize(obtainCard);
    return CARD_HELPER.deleteCardInTheGroup(card, groupName);
  }

  public String getActualUrl(final String tinyUrl) {
    CARD_HELPER.initialize(obtainCard);
    return CARD_HELPER.getActualUrl(tinyUrl);
  }
}
