package com.go.tiny.business.port;

import com.go.tiny.business.model.User;

import java.util.List;

public interface RequestUser {
  Boolean register(final User user);

  Boolean signIn(final User user);

  List<User> getAll();
}
