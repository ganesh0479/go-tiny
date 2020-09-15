package com.go.tiny.business.domain;

import com.go.tiny.business.model.User;
import com.go.tiny.business.port.ObtainUser;
import com.go.tiny.business.port.RequestUser;

import static com.go.tiny.business.helper.UserHelper.USER_HELPER;

public class UserDomain implements RequestUser {
  private ObtainUser obtainUser;

  public UserDomain(ObtainUser obtainUser) {
    this.obtainUser = obtainUser;
  }

  @Override
  public Boolean register(final User user) {
    USER_HELPER.initialize(obtainUser);
    return USER_HELPER.register(user);
  }

  @Override
  public Boolean signIn(final User user) {
    USER_HELPER.initialize(obtainUser);
    return USER_HELPER.signIn(user);
  }
}
