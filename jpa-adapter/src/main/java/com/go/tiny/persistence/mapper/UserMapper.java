package com.go.tiny.persistence.mapper;

import com.go.tiny.business.model.User;
import com.go.tiny.persistence.entity.UserEntity;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public enum UserMapper {
  USER_MAPPER;

  public Optional<UserEntity> constructUserEntity(final User user) {
    return ofNullable(
        UserEntity.builder()
            .name(user.getName())
            .emailId(user.getEmailId())
            .password(user.getPassword())
            .build());
  }
}
