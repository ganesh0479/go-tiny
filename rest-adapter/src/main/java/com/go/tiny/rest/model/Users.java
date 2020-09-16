package com.go.tiny.rest.model;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Users {
  List<UserRequest> users;
}
