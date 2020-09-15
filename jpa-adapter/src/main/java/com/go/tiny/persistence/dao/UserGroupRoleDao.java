package com.go.tiny.persistence.dao;

import com.go.tiny.persistence.entity.UserGroupRoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupRoleDao extends CrudRepository<UserGroupRoleEntity, Long> {
  List<UserGroupRoleEntity> getByUserName(final String userName);
}
