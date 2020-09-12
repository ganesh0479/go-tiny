package com.go.tiny.rest.controller;

import com.go.tiny.business.port.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class TinyRestTestConfiguration {

  @MockBean public RequestUser requestUser;
  @MockBean public RequestUserGroupRole requestUserGroupRole;
  @MockBean public RequestGroup requestGroup;
  @MockBean public RequestCard requestCard;
  @MockBean public RequestCardGroup requestCardGroup;
}
