package com.go.tiny.rest.controller;

import com.go.tiny.business.model.CardGroup;
import com.go.tiny.business.port.RequestCardGroup;
import com.go.tiny.rest.model.CardGroupRequest;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(classes = TinyRestTestConfiguration.class)
public class CardGroupControllerTest {
  @Autowired private TestRestTemplate testRestTemplate;
  @LocalServerPort private int randomServerPort;
  @Autowired private RequestCardGroup requestCardGroup;

  @Test
  @DisplayName("should be able to create card group with the support of stub")
  public void shouldBeAbleToCreateCardGroupWithTheSupportOfStub() { // Given
    CardGroupRequest cardGroupRequest = constructCardGroupRequest();
    lenient().doNothing().when(requestCardGroup).add(any(CardGroup.class));
    // When
    String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/go-tiny/card-group";
    ResponseEntity<HttpStatus> createCardResponse =
        this.testRestTemplate.postForEntity(baseUrl, cardGroupRequest, HttpStatus.class);
    // Then
    assertThat(createCardResponse.getStatusCode()).matches(HttpStatus::is2xxSuccessful);
    verify(requestCardGroup).add(any(CardGroup.class));
  }

  private CardGroupRequest constructCardGroupRequest() {
    return CardGroupRequest.builder()
        .cardName("TINY_CARD")
        .groupName("TINY_GROUP")
        .addedBy("TINY_USER")
        .build();
  }

  private CardGroup constructCardGroup() {
    return CardGroup.builder()
        .cardName("TINY_CARD")
        .groupName("TINY_GROUP")
        .addedBy("TINY_USER")
        .build();
  }
}
