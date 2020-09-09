package com.go.tiny.business.port;

import com.go.tiny.business.model.User;

public interface RequestUser {
  void register(final User user);

  Boolean signIn(final User user);
}
