package com.go.tiny.rest.controller;

import com.go.tiny.business.model.User;
import com.go.tiny.business.port.RequestUser;
import com.go.tiny.rest.model.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import static com.go.tiny.rest.mapper.UserMapper.USER_MAPPER;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/go-tiny/users")
public class UserController {
  private RequestUser requestUser;

  public UserController(RequestUser requestUser) {
    this.requestUser = requestUser;
  }

  @PostMapping
  public ResponseEntity<HttpStatus> registerUser(@RequestBody UserRequest userRequest) {
    User user = USER_MAPPER.constructUser(userRequest);
    requestUser.register(user);
    return ResponseEntity.ok(OK);
  }

  @PatchMapping
  public ResponseEntity<Boolean> signIn(@RequestBody UserRequest userRequest) {
    User user = USER_MAPPER.constructUserToSignIn(userRequest);
    return ResponseEntity.ok(requestUser.signIn(user));
  }
}
