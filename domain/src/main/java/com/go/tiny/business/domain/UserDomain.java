package com.go.tiny.business.domain;

import com.go.tiny.business.model.User;
import com.go.tiny.business.port.ObtainUser;

import static com.go.tiny.business.helper.UserHelper.USER_HELPER;

public class UserDomain {
  private ObtainUser obtainUser;

  public UserDomain(ObtainUser obtainUser) {
    this.obtainUser = obtainUser;
  }

  public void register(final User user) {
    USER_HELPER.initialize(obtainUser);
    USER_HELPER.register(user);
  }

  public Boolean signIn(final User user) {
    USER_HELPER.initialize(obtainUser);
    return USER_HELPER.signIn(user);
  }
}
