package com.go.tiny.persistence.adapter;

import com.go.tiny.business.model.User;
import com.go.tiny.business.port.ObtainUser;
import com.go.tiny.persistence.dao.UserDao;
import com.go.tiny.persistence.entity.UserEntity;

import java.util.Optional;

import static com.go.tiny.persistence.mapper.UserMapper.USER_MAPPER;
import static java.lang.Boolean.FALSE;
import static java.util.Objects.isNull;

public class UserAdapter implements ObtainUser {
  private UserDao userDao;

  public UserAdapter(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public void register(final User user) {
    if (isNull(user)) {
      return;
    }
    Optional<UserEntity> userEntity = USER_MAPPER.constructUserEntity(user);
    userEntity.ifPresent(userEntityToSave -> userDao.save(userEntityToSave));
  }

  @Override
  public Boolean signIn(final User user) {
    if (isNull(user)) {
      return FALSE;
    }
    return userDao.getByEmailIdAndPassword(user.getEmailId(), user.getPassword()).isPresent();
  }
}
