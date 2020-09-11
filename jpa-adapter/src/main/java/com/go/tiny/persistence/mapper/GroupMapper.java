package com.go.tiny.persistence.mapper;

import com.go.tiny.business.model.Group;
import com.go.tiny.persistence.entity.CardGroupEntity;
import com.go.tiny.persistence.entity.GroupEntity;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public enum GroupMapper {
  GROUP_MAPPER;

  public Optional<GroupEntity> constructGroupEntity(final Group group) {
    return ofNullable(
        GroupEntity.builder().name(group.getName()).createdBy(group.getCreatedBy()).build());
  }

  public Optional<CardGroupEntity> updateCardGroupEntityStatus(
      final CardGroupEntity cardGroupEntity, final String status) {
    return ofNullable(cardGroupEntity.toBuilder().status(status).build());
  }
}
