package com.go.tiny.business.helper;

import com.go.tiny.business.model.User;
import com.go.tiny.business.port.ObtainUser;

import static java.lang.Boolean.FALSE;

public enum UserHelper {
  USER_HELPER;
  private ObtainUser obtainUser;

  public void initialize(ObtainUser obtainUser) {
    this.obtainUser = obtainUser;
  }

  public void register(final User user) {}

  public Boolean signIn(final User user) {
    return FALSE;
  }
}
