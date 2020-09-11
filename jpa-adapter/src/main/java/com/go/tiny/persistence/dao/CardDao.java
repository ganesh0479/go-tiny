package com.go.tiny.persistence.dao;

import com.go.tiny.persistence.entity.CardEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardDao extends CrudRepository<CardEntity, Long> {
  Optional<CardEntity> getByName(final String name);

  void deleteByName(final String name);

  List<CardEntity> getAllByNameIn(final List<String> names);

  Optional<CardEntity> getByTinyUrl(final String tinyUrl);
}
