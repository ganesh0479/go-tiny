package com.go.tiny.business.port;

import com.go.tiny.business.model.User;

public interface RequestUser {
  Boolean register(final User user);

  Boolean signIn(final User user);
}
