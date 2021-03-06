package com.go.tiny.business.port;

import com.go.tiny.business.model.User;

import java.util.List;

public interface ObtainUser {
  Boolean register(final User user);

  Boolean signIn(final User user);

  Boolean checkForUserExistence(final String email);

  List<User> getAll();
}
