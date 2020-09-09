package com.go.tiny.domain.port;

import com.go.tiny.domain.model.User;

public interface RequestUser {
  void register(User user);

  void signIn(User user);
}