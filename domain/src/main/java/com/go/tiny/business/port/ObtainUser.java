package com.go.tiny.business.port;

import com.go.tiny.business.model.User;

public interface ObtainUser {
  void register(final User user);

  void signIn(final User user);
}
