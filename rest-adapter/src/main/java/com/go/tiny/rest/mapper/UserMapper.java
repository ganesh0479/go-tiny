package com.go.tiny.rest.mapper;

import com.go.tiny.business.model.User;
import com.go.tiny.rest.model.UserRequest;

public enum UserMapper {
  USER_MAPPER;

  public User constructUser(final UserRequest userRequest) {
    return User.builder()
        .name(userRequest.getName())
        .emailId(userRequest.getEmailId())
        .password(userRequest.getPassword())
        .createdOn(userRequest.getCreatedOn())
        .build();
  }

  public User constructUserToSignIn(final UserRequest userRequest) {
    return User.builder()
        .emailId(userRequest.getEmailId())
        .password(userRequest.getPassword())
        .build();
  }
}
