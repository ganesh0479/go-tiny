package com.go.tiny.persistence.dao;

import com.go.tiny.persistence.entity.SequenceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceDao extends CrudRepository<SequenceEntity, Long> {}
