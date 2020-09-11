package com.go.tiny.persistence.dao;

import com.go.tiny.persistence.entity.CardEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardDao extends CrudRepository<CardEntity, Long> {}
