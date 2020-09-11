package com.go.tiny.persistence.dao;

import com.go.tiny.persistence.entity.GroupEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupDao extends CrudRepository<GroupEntity, Long> {}
