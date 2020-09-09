package com.go.tiny.business.domain;

import com.go.tiny.business.port.ObtainUser;

public class UserDomain {
  private ObtainUser obtainUser;

  public UserDomain(ObtainUser obtainUser) {
    this.obtainUser = obtainUser;
  }
}
