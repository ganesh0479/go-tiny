package com.go.tiny.persistence.dao;

import com.go.tiny.persistence.entity.UserGroupRoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRoleDao extends CrudRepository<UserGroupRoleEntity, Long> {}
