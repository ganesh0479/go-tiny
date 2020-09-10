package com.go.tiny.business.helper;

import com.go.tiny.business.model.User;
import com.go.tiny.business.port.ObtainUser;

import static java.util.Objects.isNull;

public enum UserHelper {
  USER_HELPER;
  private ObtainUser obtainUser;

  public void initialize(ObtainUser obtainUser) {
    this.obtainUser = obtainUser;
  }

  public void register(final User user) {
    if (isPortNotAvailable()) {
      throw new NullPointerException();
    }
    this.obtainUser.register(user);
  }

  public Boolean signIn(final User user) {
    if (isPortNotAvailable()) {
      throw new NullPointerException();
    }
    return this.obtainUser.signIn(user);
  }

  private Boolean isPortNotAvailable() {
    return isNull(obtainUser);
  }
}
