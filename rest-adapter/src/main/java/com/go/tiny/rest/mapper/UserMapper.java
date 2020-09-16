package com.go.tiny.rest.mapper;

import com.go.tiny.business.model.User;
import com.go.tiny.rest.model.UserRequest;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

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

  private UserRequest constructUserResponse(final User userRequest) {
    return UserRequest.builder()
        .name(userRequest.getName())
        .emailId(userRequest.getEmailId())
        .password(userRequest.getPassword())
        .createdOn(userRequest.getCreatedOn())
        .build();
  }

  public List<UserRequest> constructUserResponse(final List<User> users) {
    if (CollectionUtils.isEmpty(users)) {
      return emptyList();
    }
    return users.stream().map(this::constructUserResponse).collect(Collectors.toList());
  }
}
