package com.go.tiny.business.domain;

import com.go.tiny.business.model.Card;
import com.go.tiny.business.port.ObtainCard;
import com.go.tiny.business.port.RequestCard;

import java.util.List;

import static com.go.tiny.business.helper.CardHelper.CARD_HELPER;

public class CardDomain implements RequestCard {
  private ObtainCard obtainCard;

  public CardDomain(ObtainCard obtainCard) {
    this.obtainCard = obtainCard;
  }

  @Override
  public Card create(final Card card) {
    CARD_HELPER.initialize(obtainCard);
    return CARD_HELPER.create(card);
  }

  @Override
  public void update(final Card card) {
    CARD_HELPER.initialize(obtainCard);
    CARD_HELPER.update(card);
  }

  @Override
  public void delete(final String name) {
    CARD_HELPER.initialize(obtainCard);
    CARD_HELPER.delete(name);
  }

  @Override
  public Card get(final String name) {
    CARD_HELPER.initialize(obtainCard);
    return CARD_HELPER.get(name);
  }

  @Override
  public List<Card> getCardsNotBelongToGroup() {
    CARD_HELPER.initialize(obtainCard);
    return CARD_HELPER.getAll();
  }

  @Override
  public String uploadAvatar(final byte[] fileData, final String cardName) {
    CARD_HELPER.initialize(obtainCard);
    return CARD_HELPER.uploadAvatar(fileData, cardName);
  }

  @Override
  public List<Card> getCardsBelongToGroup(final String groupName) {
    CARD_HELPER.initialize(obtainCard);
    return CARD_HELPER.getCardsBelongToGroup(groupName);
  }

  @Override
  public List<Card> getCardsBelongToGroupByStatus(final String groupName, final String status) {
    CARD_HELPER.initialize(obtainCard);
    return CARD_HELPER.getCardsBelongToGroupByStatus(groupName, status);
  }

  @Override
  public String updateCardInTheGroup(final Card card, final String groupName) {
    CARD_HELPER.initialize(obtainCard);
    return CARD_HELPER.updateCardInTheGroup(card, groupName);
  }

  @Override
  public String deleteCardInTheGroup(final Card card, final String groupName) {
    CARD_HELPER.initialize(obtainCard);
    return CARD_HELPER.deleteCardInTheGroup(card, groupName);
  }

  @Override
  public String getActualUrl(final String tinyUrl) {
    CARD_HELPER.initialize(obtainCard);
    return CARD_HELPER.getActualUrl(tinyUrl);
  }
}
