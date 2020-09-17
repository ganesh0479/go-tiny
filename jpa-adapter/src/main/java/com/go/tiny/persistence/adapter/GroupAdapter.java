package com.go.tiny.persistence.adapter;

import com.go.tiny.business.constant.Role;
import com.go.tiny.business.model.Group;
import com.go.tiny.business.model.UserGroupRole;
import com.go.tiny.business.port.ObtainGroup;
import com.go.tiny.persistence.dao.CardDao;
import com.go.tiny.persistence.dao.CardGroupDao;
import com.go.tiny.persistence.dao.GroupDao;
import com.go.tiny.persistence.dao.UserGroupRoleDao;
import com.go.tiny.persistence.entity.CardGroupEntity;
import com.go.tiny.persistence.entity.GroupEntity;
import com.go.tiny.persistence.entity.UserGroupRoleEntity;

import java.util.Optional;

import static com.go.tiny.persistence.constant.GoTinyJpaConstant.*;
import static com.go.tiny.persistence.mapper.GroupMapper.GROUP_MAPPER;
import static com.go.tiny.persistence.mapper.UserGroupRoleMapper.USER_GROUP_ROLE_MAPPER;
import static java.util.Objects.isNull;

public class GroupAdapter implements ObtainGroup {
  private GroupDao groupDao;
  private CardGroupDao cardGroupDao;
  private CardDao cardDao;
  private UserGroupRoleDao userGroupRoleDao;

  public GroupAdapter(
      GroupDao groupDao,
      CardGroupDao cardGroupDao,
      CardDao cardDao,
      UserGroupRoleDao userGroupRoleDao) {
    this.groupDao = groupDao;
    this.cardGroupDao = cardGroupDao;
    this.cardDao = cardDao;
    this.userGroupRoleDao = userGroupRoleDao;
  }

  @Override
  public Boolean create(final Group group) {
    if (isNull(group)) {
      return false;
    }
    Optional<GroupEntity> groupEntity = GROUP_MAPPER.constructGroupEntity(group);
    return groupEntity
        .map(
            groupEntityToCreate -> {
              GroupEntity createdGroupEntity = this.groupDao.save(groupEntityToCreate);
              Optional<UserGroupRoleEntity> userGroupRoleEntity =
                  USER_GROUP_ROLE_MAPPER.constructUserGroupRoleEntity(
                      UserGroupRole.builder()
                          .groupName(createdGroupEntity.getName())
                          .role(Role.ADMIN)
                          .userName(createdGroupEntity.getCreatedBy())
                          .addedBy(createdGroupEntity.getCreatedBy())
                          .build());
              return userGroupRoleEntity
                  .map(
                      userGroupRoleEntityToCreate ->
                          userGroupRoleDao.save(userGroupRoleEntityToCreate))
                  .isPresent();
            })
        .isPresent();
  }

  @Override
  public void authorizeCardToDisplayInGroup(final String groupName, final String cardName) {
    if (isNull(groupName) || isNull(cardName)) {
      return;
    }
    Optional<CardGroupEntity> cardGroupEntity =
        cardGroupDao.getByGroupNameAndCardName(groupName, cardName);
    Optional<CardGroupEntity> updatedCardGroupEntity =
        cardGroupEntity.flatMap(
            cardGroupEntityToUpdate ->
                GROUP_MAPPER.updateCardGroupEntityStatus(cardGroupEntityToUpdate, AUTHORIZED));
    updatedCardGroupEntity.ifPresent(
        cardGroupEntityToSave -> cardGroupDao.save(cardGroupEntityToSave));
  }

  @Override
  public void approveCardChangesInTheGroup(final String groupName, final String cardName) {
    if (isNull(groupName) || isNull(cardName)) {
      return;
    }
    Optional<CardGroupEntity> cardGroupEntity =
        cardGroupDao.getByGroupNameAndCardName(groupName, cardName);
    String status = cardGroupEntity.map(CardGroupEntity::getStatus).orElse(null);
    String name = cardGroupEntity.map(CardGroupEntity::getCardName).orElse(null);
    if (UPDATE_PENDING.equals(status)) {
      Optional<CardGroupEntity> updatedCardGroupEntity =
          cardGroupEntity.flatMap(
              cardGroupEntityToUpdate ->
                  GROUP_MAPPER.updateCardGroupEntityStatus(cardGroupEntityToUpdate, APPROVED));
      updatedCardGroupEntity.ifPresent(
          cardGroupEntityToSave -> cardGroupDao.save(cardGroupEntityToSave));
    } else if (DELETE_PENDING.equals(status)) {
      cardDao.deleteByName(name);
    }
  }
}
