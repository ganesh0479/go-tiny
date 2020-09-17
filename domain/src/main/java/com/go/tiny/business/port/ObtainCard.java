package com.go.tiny.business.port;

import com.go.tiny.business.model.Card;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ObtainCard {
  Optional<Card> create(final Card card);

  void update(final Card card);

  void delete(final String name);

  Optional<Card> get(final String name);

  List<Card> getCardsNotBelongToGroup();

  List<Card> getCardsBelongToGroup(final String groupName);

  List<Card> getCardsBelongToGroupByStatus(final String groupName, final String status);

  String updateCardInTheGroup(final Card card, final String groupName);

  String deleteCardInTheGroup(final Card card, final String groupName);

  long getUniqueId();

  String uploadAvatar(final byte[] fileData, final String cardName);

  Boolean checkTinyUrlAlreadyExist(final String groupName, String tinyUrl);

  Set<String> getAvailableShortUrls(final String groupName, String tinyUrl);

  Set<String> getAvailableShortURLsByFilteringExistingUrls(
      final String groupName, final Set<String> generatedShortUrls);
}
