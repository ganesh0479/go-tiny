package com.go.tiny.rest.controller;

import com.go.tiny.business.model.Group;
import com.go.tiny.business.port.RequestGroup;
import com.go.tiny.rest.model.GroupRequest;
import com.go.tiny.rest.model.Status;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(classes = TinyRestTestConfiguration.class)
public class GroupControllerTest {
  @Autowired private TestRestTemplate testRestTemplate;
  @LocalServerPort private int randomServerPort;
  @Autowired private RequestGroup requestGroup;

  @Test
  @DisplayName("should be able to create group with the support of stub")
  public void shouldBeAbleToCreateCardGroupWithTheSupportOfStub() {
    // Given
    GroupRequest groupRequest = constructGroupRequest();
    lenient().when(requestGroup.create(any(Group.class))).thenReturn(true);
    // When
    String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/go-tiny/groups";
    ResponseEntity<Status> createGroupResponse =
        this.testRestTemplate.postForEntity(baseUrl, groupRequest, Status.class);
    // Then
    assertThat(createGroupResponse.getStatusCode()).matches(HttpStatus::is2xxSuccessful);
    assertThat(createGroupResponse.getBody().getIsGroupCreated()).isTrue();
    verify(requestGroup).create(any(Group.class));
  }

  @Test
  @DisplayName("should be able to authorize card in group with the support of stub")
  public void shouldBeAbleToAuthorizeCardInGroupWithTheSupportOfStub() {
    // Given
    String groupName = "TINY-GROUP";
    String cardName = "TINY-CARD";
    lenient()
        .doNothing()
        .when(requestGroup)
        .authorizeCardToDisplayInGroup(anyString(), anyString());
    // When
    String baseUrl =
        "http://localhost:"
            + randomServerPort
            + "/api/v1/go-tiny/groups/"
            + groupName
            + "/cards/"
            + cardName
            + "/authorize";
    ResponseEntity<String> getGroupResponse =
        this.testRestTemplate.getForEntity(baseUrl, String.class);
    // Then
    assertThat(getGroupResponse.getStatusCode()).matches(HttpStatus::is2xxSuccessful);
    verify(requestGroup).authorizeCardToDisplayInGroup(anyString(), anyString());
  }

  @Test
  @DisplayName("should be able to approve card in group with the support of stub")
  public void shouldBeAbleToApproveCardInGroupWithTheSupportOfStub() {
    // Given
    String groupName = "TINY-GROUP";
    String cardName = "TINY-CARD";
    lenient().doNothing().when(requestGroup).approveCardChangesInTheGroup(anyString(), anyString());
    // When
    String baseUrl =
        "http://localhost:"
            + randomServerPort
            + "/api/v1/go-tiny/groups/"
            + groupName
            + "/cards/"
            + cardName
            + "/approve";
    ResponseEntity<String> getGroupResponse =
        this.testRestTemplate.exchange(baseUrl, HttpMethod.GET, null, String.class);
    // Then
    assertThat(getGroupResponse.getStatusCode()).matches(HttpStatus::is2xxSuccessful);
    verify(requestGroup).approveCardChangesInTheGroup(anyString(), anyString());
  }

  private GroupRequest constructGroupRequest() {
    return GroupRequest.builder().name("TINY-GROUP").createdBy("TINY-USER").build();
  }
}
