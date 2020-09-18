package com.go.tiny.rest.controller;

import com.go.tiny.business.model.UserGroupRole;
import com.go.tiny.business.port.RequestUserGroupRole;
import com.go.tiny.rest.model.Status;
import com.go.tiny.rest.model.UserGroupRoleRequest;
import com.go.tiny.rest.model.UserGroupRoleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
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

  @Operation(summary = "User should be able to update user groups")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Able to update user groups",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Status.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Unable to update user groups",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error. Unable to update the user groups",
            content = @Content)
      })
  @PatchMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Status> updateUserGroupRole(
      @RequestBody UserGroupRoleRequest userGroupRoleRequest) {
    UserGroupRole userGroupRole =
        USER_GROUP_ROLE_MAPPER.constructUserGroupRole(userGroupRoleRequest);
    requestUserGroupRole.updateUserGroupRole(userGroupRole);
    return ResponseEntity.ok(Status.builder().status(OK.toString()).build());
  }

  @Operation(summary = "Get all the user groups of Go Tiny application")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the user groups",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserGroupRoleResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Unable to find the user groups",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error. Unable to get the user groups",
            content = @Content)
      })
  @GetMapping(value = "/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserGroupRoleResponse> getUserGroups(
      @Parameter(description = "email id of the user") @PathVariable String userName) {
    List<UserGroupRole> userGroupRoles = requestUserGroupRole.getGroups(userName);
    return ResponseEntity.ok(
        UserGroupRoleResponse.builder()
            .userGroupRolesResponse(
                USER_GROUP_ROLE_MAPPER.constructUSerGroupRolesResponse(userGroupRoles))
            .build());
  }
}
