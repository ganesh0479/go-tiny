package com.go.tiny.persistence.mapper;

import com.go.tiny.business.model.User;
import com.go.tiny.persistence.entity.UserEntity;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
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

  public User constructUser(final UserEntity user) {
    return User.builder()
        .name(user.getName())
        .emailId(user.getEmailId())
        .password(user.getPassword())
        .build();
  }

  public List<User> constructUsers(final List<UserEntity> userEntities) {
    if (CollectionUtils.isEmpty(userEntities)) {
      return emptyList();
    }
    return userEntities.stream()
        .filter(Objects::nonNull)
        .map(this::constructUser)
        .collect(Collectors.toList());
  }
}
