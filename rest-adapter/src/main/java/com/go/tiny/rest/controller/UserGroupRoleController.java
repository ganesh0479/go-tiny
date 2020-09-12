package com.go.tiny.rest.controller;

import com.go.tiny.business.model.UserGroupRole;
import com.go.tiny.business.port.RequestUserGroupRole;
import com.go.tiny.rest.model.UserGroupRoleRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.go.tiny.rest.mapper.UserGroupRoleMapper.USER_GROUP_ROLE_MAPPER;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/go-tiny/user-group-role")
public class UserGroupRoleController {
  private RequestUserGroupRole requestUserGroupRole;

  public UserGroupRoleController(RequestUserGroupRole requestUserGroupRole) {
    this.requestUserGroupRole = requestUserGroupRole;
  }

  @PatchMapping
  public ResponseEntity<HttpStatus> updateUserGroupRole(
      @RequestBody UserGroupRoleRequest userGroupRoleRequest) {
    UserGroupRole userGroupRole =
        USER_GROUP_ROLE_MAPPER.constructUserGroupRole(userGroupRoleRequest);
    requestUserGroupRole.updateUserGroupRole(userGroupRole);
    return ResponseEntity.ok(OK);
  }
}
