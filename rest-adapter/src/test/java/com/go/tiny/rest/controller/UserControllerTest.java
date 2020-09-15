package com.go.tiny.rest.controller;

import com.go.tiny.business.model.User;
import com.go.tiny.business.port.RequestUser;
import com.go.tiny.rest.model.Status;
import com.go.tiny.rest.model.UserRequest;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(classes = TinyRestTestConfiguration.class)
public class UserControllerTest {
  @Autowired private TestRestTemplate testRestTemplate;
  @LocalServerPort private int randomServerPort;
  @Autowired private RequestUser requestUser;

  @Test
  @DisplayName("should be able to register the user with the support of stub")
  public void shouldBeAbleToRegisterTheUserWithTheSupportOfStub() {
    // Given
    UserRequest userRequest = constructUserRequest();
    lenient().when(requestUser.register(any(User.class))).thenReturn(true);
    // When
    String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/go-tiny/users";
    ResponseEntity<Status> registerResponse =
        this.testRestTemplate.postForEntity(baseUrl, userRequest, Status.class);
    // Then
    assertThat(registerResponse.getStatusCode()).matches(HttpStatus::is2xxSuccessful);
    assertThat(registerResponse.getBody().getSignInSuccess()).isTrue();
    verify(requestUser).register(any(User.class));
  }

  @Test
  @DisplayName("should be able to sign in the user with the support of stub")
  public void shouldBeAbleToSignInTheUserWithTheSupportOfStub() {
    // Given
    UserRequest userRequest = constructUserRequest();
    lenient().when(requestUser.signIn(any(User.class))).thenReturn(Boolean.TRUE);
    // When
    String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/go-tiny/users";
    Status registerResponse =
        this.getRestTemplateForPatch().patchForObject(baseUrl, userRequest, Status.class);
    // Then
    assertThat(registerResponse.getSignInSuccess()).isTrue();
    verify(requestUser).signIn(any(User.class));
  }

  private UserRequest constructUserRequest() {
    return UserRequest.builder()
        .name("TINY USER")
        .emailId("user@tiny.com")
        .password("tinypwd@1")
        .createdOn(LocalDate.of(2020, 10, 9))
        .build();
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
}
