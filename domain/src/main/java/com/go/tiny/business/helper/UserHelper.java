package com.go.tiny.business.helper;

import com.go.tiny.business.exception.GoTinyDomainException;
import com.go.tiny.business.model.User;
import com.go.tiny.business.port.ObtainUser;

import static com.go.tiny.business.exception.GoTinyDomainExceptionMessage.USER_RIGHT_SIDE_PORT_UNAVAILABLE;
import static java.util.Objects.isNull;

public enum UserHelper {
  USER_HELPER;
  private ObtainUser obtainUser;

  public void initialize(ObtainUser obtainUser) {
    this.obtainUser = obtainUser;
  }

  public void register(final User user) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(USER_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    this.obtainUser.register(user);
  }

  public Boolean signIn(final User user) {
    if (isPortNotAvailable()) {
      throw new GoTinyDomainException(USER_RIGHT_SIDE_PORT_UNAVAILABLE);
    }
    return this.obtainUser.signIn(user);
  }

  private Boolean isPortNotAvailable() {
    return isNull(obtainUser);
  }
}
