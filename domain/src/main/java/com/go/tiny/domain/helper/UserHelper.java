package com.go.tiny.domain.helper;

import com.go.tiny.domain.model.User;

public enum UserHelper {
  USER_HELPER;

  public User addUser(User user) {
    User response = new User(user.emailId(),user.password());
    return response;
  }
}
