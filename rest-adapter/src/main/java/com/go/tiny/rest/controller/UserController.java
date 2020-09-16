package com.go.tiny.rest.controller;

import com.go.tiny.business.model.User;
import com.go.tiny.business.port.RequestUser;
import com.go.tiny.rest.model.Status;
import com.go.tiny.rest.model.UserRequest;
import com.go.tiny.rest.model.Users;
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

  @PostMapping
  public ResponseEntity<Status> registerUser(@RequestBody UserRequest userRequest) {
    User user = USER_MAPPER.constructUser(userRequest);
    return ResponseEntity.ok(Status.builder().signInSuccess(requestUser.register(user)).build());
  }

  @PatchMapping
  public ResponseEntity<Status> signIn(@RequestBody UserRequest userRequest) {
    User user = USER_MAPPER.constructUserToSignIn(userRequest);
    return ResponseEntity.ok(Status.builder().signInSuccess(requestUser.signIn(user)).build());
  }

  @GetMapping
  public ResponseEntity<Users> getAll() {
    List<UserRequest> userResponse = USER_MAPPER.constructUserResponse(requestUser.getAll());
    return ResponseEntity.ok(Users.builder().users(userResponse).build());
  }
}
