package com.go.tiny.rest.controller;

import com.go.tiny.business.model.UserGroupRole;
import com.go.tiny.business.port.RequestUserGroupRole;
import com.go.tiny.rest.model.UserGroupRoleRequest;
import com.go.tiny.rest.model.UserGroupRoleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.go.tiny.rest.mapper.UserGroupRoleMapper.USER_GROUP_ROLE_MAPPER;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/go-tiny/user-group-role")
@CrossOrigin(origins = "*")
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

  @GetMapping("/{userName}")
  public ResponseEntity<UserGroupRoleResponse> getUserGroups(@PathVariable String userName) {
    List<UserGroupRole> userGroupRoles = requestUserGroupRole.getGroups(userName);
    return ResponseEntity.ok(
        UserGroupRoleResponse.builder()
            .userGroupRoleRequests(
                USER_GROUP_ROLE_MAPPER.constructUSerGroupRolesResponse(userGroupRoles))
            .build());
  }
}
