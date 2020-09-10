package com.go.tiny.business.helper;

import com.go.tiny.business.model.Card;
import com.go.tiny.business.port.ObtainCard;

import java.util.List;

import static java.util.Collections.emptyList;

public enum CardHelper {
  CARD_HELPER;
  private ObtainCard obtainCard;

  public void initialize(ObtainCard obtainCard) {
    this.obtainCard = obtainCard;
  }

  public Card create(final Card card) {
    return null;
  }

  public void update(final Card card) {}

  public void delete(final String name) {}

  public Card get(final String name) {
    return null;
  }

  public List<Card> getAll() {
    return null;
  }

  public List<Card> getCardsBelongToGroup(final String groupName) {
    return emptyList();
  }

  public List<Card> getCardsBelongToGroupByStatus(final String groupName, final String status) {
    return emptyList();
  }

  public String updateCardInTheGroup(final Card card, final String groupName) {
    return null;
  }

  public String deleteCardInTheGroup(final Card card, final String groupName) {
    return null;
  }

  public String getActualUrl(final String tinyUrl) {
    return null;
  }
}
