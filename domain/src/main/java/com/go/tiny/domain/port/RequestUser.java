package com.go.tiny.domain.port;

import com.go.tiny.domain.model.User;

public interface RequestUser {
  void register(final User user);

  void signIn(final User user);
}
