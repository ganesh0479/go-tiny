package com.go.tiny.rest.controller;

import com.go.tiny.business.port.RequestCard;
import com.go.tiny.business.port.RequestGroup;
import com.go.tiny.business.port.RequestUser;
import com.go.tiny.business.port.RequestUserGroupRole;
import com.go.tiny.business.port.RequestCardGroup;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class TinyRestTestConfiguration {

  @MockBean private RequestUser requestUser;
  @MockBean private RequestUserGroupRole requestUserGroupRole;
  @MockBean private RequestGroup requestGroup;
  @MockBean private RequestCard requestCard;
  @MockBean private RequestCardGroup requestCardGroup;
}
