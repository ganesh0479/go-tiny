package com.go.tiny.rest.controller;

import com.go.tiny.business.model.UserGroupRole;
import com.go.tiny.business.port.RequestUserGroupRole;
import com.go.tiny.rest.model.Status;
import com.go.tiny.rest.model.UserGroupRoleRequest;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.go.tiny.business.constant.Role.ADMIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(classes = TinyRestTestConfiguration.class)
public class UserGroupRoleControllerTest {
  @LocalServerPort private int randomServerPort;
  @Autowired private RequestUserGroupRole requestUserGroupRole;

  @Test
  @DisplayName("should be able to sign in the user with the support of stub")
  public void shouldBeAbleToSignInTheUserWithTheSupportOfStub() {
    // Given
    UserGroupRoleRequest userGroupRoleRequest = constructUserGroupRoleRequest();
    lenient().doNothing().when(requestUserGroupRole).updateUserGroupRole(any(UserGroupRole.class));
    // When
    String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/go-tiny/user-group-role";
    Status registerResponse =
        this.getRestTemplateForPatch().patchForObject(baseUrl, userGroupRoleRequest, Status.class);
    // Then
    assertThat(registerResponse);
    verify(requestUserGroupRole).updateUserGroupRole(any(UserGroupRole.class));
  }

  private RestTemplate getRestTemplateForPatch() {
    final int timeout = 5;
    final HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
        new HttpComponentsClientHttpRequestFactory();
    clientHttpRequestFactory.setConnectTimeout(timeout * 1000);
    final RestTemplate template = new RestTemplate(clientHttpRequestFactory);
    template.setMessageConverters(List.of(new MappingJackson2HttpMessageConverter()));
    return template;
  }

  private UserGroupRoleRequest constructUserGroupRoleRequest() {
    return UserGroupRoleRequest.builder()
        .groupName("TINY-GROUP")
        .role(ADMIN)
        .userName("TINY-USER")
        .addedBy("TINY-USER")
        .build();
  }
}
