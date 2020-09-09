package com.go.tiny.business.helper;

import com.go.tiny.business.model.User;

public enum UserHelper {
  USER_HELPER;

  public User addUser(User user) {
    User response = new User(user.emailId(),user.password());
    return response;
  }
}
