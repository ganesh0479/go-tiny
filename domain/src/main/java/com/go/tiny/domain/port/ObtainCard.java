package com.go.tiny.domain.port;

import com.go.tiny.domain.model.Card;

public interface ObtainCard {
  Card create(final Card card);

  void update(final Card card);

  void delete(final String name);

  Card get(final String name);
}
