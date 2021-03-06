package com.go.tiny.business.port;

import com.go.tiny.business.model.Card;

import java.util.List;

public interface RequestCard {
  Card create(final Card card);

  void update(final Card card);

  void delete(final String name);

  Card get(final String name);

  List<Card> getCardsNotBelongToGroup();

  List<Card> getCardsBelongToGroup(final String groupName);

  List<Card> getCardsBelongToGroupByStatus(final String groupName, final String status);

  String updateCardInTheGroup(final Card card, final String groupName);

  String deleteCardInTheGroup(final Card card, final String groupName);

  String uploadAvatar(final byte[] fileData, final String cardName);
}
