package com.go.tiny.persistence.dao;

import com.go.tiny.persistence.entity.CardGroupEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardGroupDao extends CrudRepository<CardGroupEntity, Long> {
  List<CardGroupEntity> getByGroupName(final String name);

  List<CardGroupEntity> getByGroupNameAndStatus(final String groupName, final String status);

  Optional<CardGroupEntity> getByGroupNameAndCardName(final String groupName, final String cardName);
}
