package com.go.tiny.persistence.adapter;

import com.go.tiny.business.model.User;
import com.go.tiny.business.port.ObtainUser;
import com.go.tiny.persistence.dao.UserDao;
import com.go.tiny.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.go.tiny.persistence.mapper.UserMapper.USER_MAPPER;
import static java.lang.Boolean.FALSE;
import static java.util.Objects.isNull;

public class UserAdapter implements ObtainUser {
  private UserDao userDao;

  public UserAdapter(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public Boolean register(final User user) {
    if (isNull(user)) {
      return false;
    }
    Optional<UserEntity> userEntity = USER_MAPPER.constructUserEntity(user);
    return userEntity.map(userEntityToSave -> userDao.save(userEntityToSave)).isPresent();
  }

  @Override
  public Boolean signIn(final User user) {
    if (isNull(user)) {
      return FALSE;
    }
    return userDao.getByEmailIdAndPassword(user.getEmailId(), user.getPassword()).isPresent();
  }

  @Override
  public Boolean checkForUserExistence(final String email) {
    if (isNull(email)) {
      return false;
    }
    return userDao.getByEmailId(email).isPresent();
  }

  @Override
  public List<User> getAll() {
    List<UserEntity> userEntities =
        StreamSupport.stream(userDao.findAll().spliterator(), false).collect(Collectors.toList());
    return USER_MAPPER.constructUsers(userEntities);
  }
}
