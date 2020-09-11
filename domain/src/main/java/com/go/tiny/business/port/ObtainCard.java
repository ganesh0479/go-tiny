package com.go.tiny.business.port;

import com.go.tiny.business.model.Card;

import java.util.List;
import java.util.Optional;

public interface ObtainCard {
  Optional<Card> create(final Card card);

  void update(final Card card);

  void delete(final String name);

  Optional<Card> get(final String name);

  List<Card> getAll();

  List<Card> getCardsBelongToGroup(final String groupName);

  List<Card> getCardsBelongToGroupByStatus(final String groupName, final String status);

  String updateCardInTheGroup(final Card card, final String groupName);

  String deleteCardInTheGroup(final Card card, final String groupName);

  String getActualUrl(final String tinyUrl);

  long getUniqueId();
}
