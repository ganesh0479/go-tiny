package com.go.tiny.business.port;

import com.go.tiny.business.model.CardGroup;

import java.util.Optional;

public interface ObtainCardGroup {
  void add(final CardGroup cardGroup);

  Optional<CardGroup> getActualUrl(final String groupName, final String tinyUrl);
}
