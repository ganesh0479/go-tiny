package com.go.tiny.rest.controller;

import com.go.tiny.business.model.Card;
import com.go.tiny.business.port.RequestCard;
import com.go.tiny.rest.model.CardRequest;
import com.go.tiny.rest.model.CreateCardResponse;
import com.go.tiny.rest.model.GetCardResponse;
import com.go.tiny.rest.model.GetCards;
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
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(classes = TinyRestTestConfiguration.class)
public class CardControllerTest {
  @Autowired private TestRestTemplate testRestTemplate;
  @LocalServerPort private int randomServerPort;
  @Autowired private RequestCard requestCard;
  private static final String CARD_NAME = "TINY-CARD";
  private static final String APPROVAL_STATUS = "APPROVED";
  private static final String TINY_URL = "http://go-tiny.com/TINY-URL";
  private static final String ACTUAL_URL = "http://random.com/ahbjkjdjjsggdgjdbnjghjdnn";

  @Test
  @DisplayName("should be able to get all the cards with the support of stub")
  public void shouldBeAbleToGetAllTheCardWithTheSupportOfStub() {
    // Given
    Card card = constructCard();
    List<Card> cards = List.of(constructCard());
    lenient().when(requestCard.getCardsNotBelongToGroup()).thenReturn(cards);
    // When
    String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/go-tiny/cards";
    ResponseEntity<GetCards> getCards = this.testRestTemplate.getForEntity(baseUrl, GetCards.class);
    // Then
    assertThat(getCards.getStatusCode()).matches(HttpStatus::is2xxSuccessful);
    assertThat(getCards.getBody().getCardResponses().get(0))
        .extracting("title", "name")
        .contains(card.getTitle(), card.getName());
    verify(requestCard).getCardsNotBelongToGroup();
  }

  @Test
  @DisplayName("should not be able to get all the cards with the support of stub")
  public void shouldNotBeAbleToGetAllTheCardWithTheSupportOfStub() {
    // Given
    List<Card> cards = List.of();
    lenient().when(requestCard.getCardsNotBelongToGroup()).thenReturn(cards);
    // When
    String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/go-tiny/cards";
    ResponseEntity<GetCards> getCards = this.testRestTemplate.getForEntity(baseUrl, GetCards.class);
    // Then
    assertThat(getCards.getStatusCode()).matches(HttpStatus::is2xxSuccessful);
    assertThat(getCards.getBody()).isNull();
    verify(requestCard).getCardsNotBelongToGroup();
  }

  @Test
  @DisplayName("should be able to create card with the support of stub")
  public void shouldBeAbleToCreateCardWithTheSupportOfStub() { // Given
    CardRequest cardRequest = constructCardRequest();
    Card card = constructCard();
    lenient().when(requestCard.create(any(Card.class))).thenReturn(card);
    // When
    String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/go-tiny/cards";
    ResponseEntity<CreateCardResponse> createCardResponse =
        this.testRestTemplate.postForEntity(baseUrl, cardRequest, CreateCardResponse.class);
    // Then
    assertThat(createCardResponse.getStatusCode()).matches(HttpStatus::is2xxSuccessful);
    assertThat(createCardResponse.getBody())
        .extracting("tinyUrl", "cardName")
        .contains(card.getTitle(), card.getName());
    verify(requestCard).create(any(Card.class));
  }

  @Test
  @DisplayName("should be able to update card with the support of stub")
  public void shouldBeAbleToUpdateCardWithTheSupportOfStub() {
    // Given
    CardRequest cardRequest = constructCardRequest();
    lenient().doNothing().when(requestCard).update(any(Card.class));
    // When
    String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/go-tiny/cards";
    HttpStatus responseEntity =
        this.getRestTemplateForPatch().patchForObject(baseUrl, cardRequest, HttpStatus.class);
    // Then
    assertThat(responseEntity.is2xxSuccessful());
    verify(requestCard).update(any(Card.class));
  }

  @Test
  @DisplayName("should be able to delete card with the support of stub")
  public void shouldBeAbleToDeleteCardWithTheSupportOfStub() {
    // Given
    String cardName = "TINY-CARD";
    lenient().doNothing().when(requestCard).delete(cardName);
    // When
    String baseUrl =
        "http://localhost:" + randomServerPort + "/api/v1/go-tiny/cards" + "/" + cardName;
    ResponseEntity<HttpStatus> responseEntity =
        this.testRestTemplate.exchange(baseUrl, HttpMethod.DELETE, null, HttpStatus.class);
    // Then
    assertThat(responseEntity.getBody()).matches(HttpStatus::is2xxSuccessful);
    verify(requestCard).delete(cardName);
  }

  @Test
  @DisplayName("should be able to get card with the support of stub")
  public void shouldBeAbleToGetCardWithTheSupportOfStub() {
    // Given
    String cardName = "TINY-CARD";
    Card card = constructCard();
    lenient().when(requestCard.get(cardName)).thenReturn(card);
    // When
    String baseUrl =
        "http://localhost:" + randomServerPort + "/api/v1/go-tiny/cards" + "/" + cardName;
    ResponseEntity<GetCardResponse> responseEntity =
        this.testRestTemplate.exchange(baseUrl, HttpMethod.GET, null, GetCardResponse.class);
    // Then
    assertThat(responseEntity.getStatusCode()).matches(HttpStatus::is2xxSuccessful);
    assertThat(responseEntity.getBody()).isNotNull();
    verify(requestCard).get(cardName);
  }

  @Test
  @DisplayName("should not be able to get card with the support of stub")
  public void shouldNotBeAbleToGetCardWithTheSupportOfStub() {
    // Given
    String cardName = "TINY-CARD";
    lenient().when(requestCard.get(cardName)).thenReturn(null);
    // When
    String baseUrl =
        "http://localhost:" + randomServerPort + "/api/v1/go-tiny/cards" + "/" + cardName;
    ResponseEntity<GetCardResponse> responseEntity =
        this.testRestTemplate.exchange(baseUrl, HttpMethod.GET, null, GetCardResponse.class);
    // Then
    assertThat(responseEntity.getStatusCode()).matches(HttpStatus::is2xxSuccessful);
    assertThat(responseEntity.getBody()).isNull();
    verify(requestCard).get(cardName);
  }

  @Test
  @DisplayName("should be able to get cards belong to group with the support of stub")
  public void shouldBeAbleToGetCardsBelongToGroupWithTheSupportOfStub() {
    // Given
    String groupName = "TINY-GROUP";
    Card card = constructCard();
    lenient().when(requestCard.getCardsBelongToGroup(groupName)).thenReturn(List.of(card));
    // When
    String baseUrl =
        "http://localhost:" + randomServerPort + "/api/v1/go-tiny/cards/groups/" + groupName;
    ResponseEntity<GetCards> responseEntity =
        this.testRestTemplate.exchange(baseUrl, HttpMethod.GET, null, GetCards.class);
    // Then
    assertThat(responseEntity.getStatusCode()).matches(HttpStatus::is2xxSuccessful);
    assertThat(responseEntity.getBody().getCardResponses()).isNotEmpty();
    verify(requestCard).getCardsBelongToGroup(groupName);
  }

  @Test
  @DisplayName("should not be able to get cards belong to group with the support of stub")
  public void shouldNotBeAbleToGetCardsBelongToGroupWithTheSupportOfStub() {
    // Given
    String groupName = "TINY-GROUP";
    lenient().when(requestCard.getCardsBelongToGroup(groupName)).thenReturn(List.of());
    // When
    String baseUrl =
        "http://localhost:" + randomServerPort + "/api/v1/go-tiny/cards/groups/" + groupName;
    ResponseEntity<GetCards> responseEntity =
        this.testRestTemplate.exchange(baseUrl, HttpMethod.GET, null, GetCards.class);
    // Then
    assertThat(responseEntity.getStatusCode()).matches(HttpStatus::is2xxSuccessful);
    assertThat(responseEntity.getBody()).isNull();
    verify(requestCard).getCardsBelongToGroup(groupName);
  }

  @Test
  @DisplayName("should be able to get cards belong to group by status with the support of stub")
  public void shouldBeAbleToGetCardsBelongToGroupByStatusWithTheSupportOfStub() {
    // Given
    String groupName = "TINY-GROUP";
    String status = "UPDATE_PENDING";
    Card card = constructCard();
    lenient()
        .when(requestCard.getCardsBelongToGroupByStatus(groupName, status))
        .thenReturn(List.of(card));
    // When
    String baseUrl =
        "http://localhost:"
            + randomServerPort
            + "/api/v1/go-tiny/cards/"
            + status
            + "/groups/"
            + groupName;
    ResponseEntity<GetCards> responseEntity =
        this.testRestTemplate.exchange(baseUrl, HttpMethod.GET, null, GetCards.class);
    // Then
    assertThat(responseEntity.getStatusCode()).matches(HttpStatus::is2xxSuccessful);
    assertThat(responseEntity.getBody().getCardResponses()).isNotEmpty();
    verify(requestCard).getCardsBelongToGroupByStatus(groupName, status);
  }

  @Test
  @DisplayName("should not be able to get cards belong to group by status with the support of stub")
  public void shouldNotBeAbleToGetCardsBelongToGroupByStatusWithTheSupportOfStub() {
    // Given
    String groupName = "TINY-GROUP";
    String status = "UPDATE_PENDING";
    lenient()
        .when(requestCard.getCardsBelongToGroupByStatus(groupName, status))
        .thenReturn(List.of());
    // When
    String baseUrl =
        "http://localhost:"
            + randomServerPort
            + "/api/v1/go-tiny/cards/"
            + status
            + "/groups/"
            + groupName;
    ResponseEntity<GetCards> responseEntity =
        this.testRestTemplate.exchange(baseUrl, HttpMethod.GET, null, GetCards.class);
    // Then
    assertThat(responseEntity.getStatusCode()).matches(HttpStatus::is2xxSuccessful);
    assertThat(responseEntity.getBody()).isNull();
    verify(requestCard).getCardsBelongToGroupByStatus(groupName, status);
  }

  @Test
  @DisplayName("should be able to delete cards belong to group by status with the support of stub")
  public void shouldBeAbleToDeleteCardsBelongToGroupByStatusWithTheSupportOfStub() {
    // Given
    String groupName = "TINY-GROUP";
    String cardName = "TINY-CARD";
    String status = "DELETE_PENDING";
    lenient()
        .when(requestCard.deleteCardInTheGroup(any(Card.class), any(String.class)))
        .thenReturn(status);
    // When
    String baseUrl =
        "http://localhost:"
            + randomServerPort
            + "/api/v1/go-tiny/cards/"
            + cardName
            + "/groups/"
            + groupName;
    ResponseEntity<String> responseEntity =
        this.testRestTemplate.exchange(baseUrl, HttpMethod.DELETE, null, String.class);
    // Then
    assertThat(responseEntity.getStatusCode()).matches(HttpStatus::is2xxSuccessful);
    assertThat(responseEntity.getBody()).matches(status);
    verify(requestCard).deleteCardInTheGroup(any(Card.class), any(String.class));
  }

  @Test
  @DisplayName("should be able to update card in the group with the support of stub")
  public void shouldBeAbleToUpdateCardInTheGroupWithTheSupportOfStub() {
    // Given
    String groupName = "TINY-GROUP";
    CardRequest cardRequest = constructCardRequest();
    lenient()
        .when(requestCard.updateCardInTheGroup(any(Card.class), anyString()))
        .thenReturn(APPROVAL_STATUS);
    // When
    String baseUrl =
        "http://localhost:" + randomServerPort + "/api/v1/go-tiny/cards/groups/" + groupName;
    Status responseEntity =
        this.getRestTemplateForPatch().patchForObject(baseUrl, cardRequest, Status.class);
    // Then
    assertThat(responseEntity.getStatus()).matches(APPROVAL_STATUS);
    verify(requestCard).updateCardInTheGroup(any(Card.class), anyString());
  }

  @Test
  @DisplayName("should be able to give actual url to redirect with the support of stub")
  public void shouldBeAbleToGiveActualUrlToRedirectWithTheSupportOfStub() {
    // Given
    String actualUrl = "ACTUAL-URL";
    lenient().when(requestCard.getActualUrl(anyString())).thenReturn(actualUrl);
    // When
    String baseUrl = "http://localhost:" + randomServerPort + "/go.tiny/groupName/CardName";
    ResponseEntity responseEntity =
        this.testRestTemplate.getForEntity(baseUrl, ResponseEntity.class);
    // Then
    assertThat(responseEntity.getStatusCode()).matches(HttpStatus::is3xxRedirection);
    verify(requestCard).getActualUrl(anyString());
  }

  private Card constructCard() {
    return Card.builder()
        .title("TINY-CARD")
        .description("CARD-DESC")
        .name(CARD_NAME)
        .actualUrl(ACTUAL_URL)
        .expiresIn(10)
        .createdBy("TINY-USER")
        .tinyUrl(TINY_URL)
        .build();
  }

  private CardRequest constructCardRequest() {
    return CardRequest.builder()
        .title("TINY-CARD")
        .description("CARD-DESC")
        .name(CARD_NAME)
        .actualUrl(ACTUAL_URL)
        .expiresIn(10)
        .createdBy("TINY-USER")
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
