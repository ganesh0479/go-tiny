package com.go.tiny.persistence.adapter;

import com.go.tiny.business.model.Card;
import com.go.tiny.business.port.ObtainCard;
import com.go.tiny.persistence.dao.CardDao;

import java.util.List;

public class CardAdapter implements ObtainCard {
  private CardDao cardDao;

  public CardAdapter(CardDao cardDao) {
    this.cardDao = cardDao;
  }

  @Override
  public Card create(Card card) {
    return null;
  }

  @Override
  public void update(Card card) {}

  @Override
  public void delete(String name) {}

  @Override
  public Card get(String name) {
    return null;
  }

  @Override
  public List<Card> getAll() {
    return null;
  }

  @Override
  public List<Card> getCardsBelongToGroup(String groupName) {
    return null;
  }

  @Override
  public List<Card> getCardsBelongToGroupByStatus(String groupName, String status) {
    return null;
  }

  @Override
  public String updateCardInTheGroup(Card card, String groupName) {
    return null;
  }

  @Override
  public String deleteCardInTheGroup(Card card, String groupName) {
    return null;
  }

  @Override
  public String getActualUrl(String tinyUrl) {
    return null;
  }

  @Override
  public long getUniqueId() {
    return 0;
  }
}
