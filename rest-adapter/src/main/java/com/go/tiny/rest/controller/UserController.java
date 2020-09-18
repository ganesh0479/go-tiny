package com.go.tiny.rest.controller;

import com.go.tiny.business.model.User;
import com.go.tiny.business.port.RequestUser;
import com.go.tiny.rest.model.Status;
import com.go.tiny.rest.model.UserRequest;
import com.go.tiny.rest.model.Users;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.go.tiny.rest.mapper.UserMapper.USER_MAPPER;

@RestController
@RequestMapping("/api/v1/go-tiny/users")
@CrossOrigin(origins = "*")
public class UserController {
  private RequestUser requestUser;

  public UserController(RequestUser requestUser) {
    this.requestUser = requestUser;
  }

  @Operation(summary = "Register user to Go Tiny")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "User should be able to register",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Status.class))
            }),
        @ApiResponse(responseCode = "404", description = "Unable to register", content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error. User not able to register",
            content = @Content)
      })
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Status> registerUser(@RequestBody UserRequest userRequest) {
    User user = USER_MAPPER.constructUser(userRequest);
    return ResponseEntity.ok(Status.builder().signInSuccess(requestUser.register(user)).build());
  }

  @Operation(summary = "Sign in user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Able to sign in the user",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Status.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "User unable to sign in",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error. User not able to Sign IN",
            content = @Content)
      })
  @PatchMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Status> signIn(@RequestBody UserRequest userRequest) {
    User user = USER_MAPPER.constructUserToSignIn(userRequest);
    return ResponseEntity.ok(Status.builder().signInSuccess(requestUser.signIn(user)).build());
  }

  @Operation(summary = "Get all the users of Go Tiny application")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the users",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Users.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Unable to find the users",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error. Unable to get the users",
            content = @Content)
      })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Users> getAll() {
    List<UserRequest> userResponse = USER_MAPPER.constructUserResponse(requestUser.getAll());
    return ResponseEntity.ok(Users.builder().users(userResponse).build());
  }
}
