package com.go.tiny.persistence.dao;

import com.go.tiny.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends CrudRepository<UserEntity, Long> {
  Optional<UserEntity> getByEmailIdAndPassword(final String emailId, final String password);
}
