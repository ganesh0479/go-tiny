package com.go.tiny.rest.controller;

import com.go.tiny.business.model.Card;
import com.go.tiny.business.port.RequestCard;
import com.go.tiny.rest.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.go.tiny.rest.mapper.CardMapper.CARD_MAPPER;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/go-tiny/cards")
public class CardController {
  private RequestCard requestCard;

  public CardController(RequestCard requestCard) {
    this.requestCard = requestCard;
  }

  @PostMapping
  public ResponseEntity<CreateCardResponse> create(@RequestBody final CardRequest cardRequest) {
    Card card = CARD_MAPPER.constructCardRequest(cardRequest);
    Card createdCard = requestCard.create(card);
    return ResponseEntity.ok(CARD_MAPPER.constructCreateCardResponse(createdCard));
  }

  @PatchMapping
  public ResponseEntity<HttpStatus> update(@RequestBody final CardRequest cardRequest) {
    Card card = CARD_MAPPER.constructCardRequestToUpdate(cardRequest);
    requestCard.update(card);
    return ResponseEntity.ok(OK);
  }

  @DeleteMapping("{cardName}")
  public ResponseEntity<HttpStatus> delete(@PathVariable final String cardName) {
    requestCard.delete(cardName);
    return ResponseEntity.ok(OK);
  }

  @GetMapping("{cardName}")
  public ResponseEntity<GetCardResponse> get(@PathVariable final String cardName) {
    Card requestedCard = requestCard.get(cardName);
    return ResponseEntity.ok(CARD_MAPPER.constructGetCardResponse(requestedCard));
  }

  @GetMapping
  public ResponseEntity<GetCards> getAll() {
    List<Card> cards = requestCard.getAll();
    return ResponseEntity.ok(CARD_MAPPER.constructGetCards(cards));
  }

  @GetMapping("/groups/{groupName}")
  public ResponseEntity<GetCards> getCardsBelongToGroup(@PathVariable final String groupName) {
    List<Card> cards = requestCard.getCardsBelongToGroup(groupName);
    return ResponseEntity.ok(CARD_MAPPER.constructGetCards(cards));
  }

  @GetMapping("{status}/groups/{groupName}")
  public ResponseEntity<GetCards> getCardsBelongToGroupByStatus(
      @PathVariable final String groupName, @PathVariable final String status) {
    List<Card> cards = requestCard.getCardsBelongToGroupByStatus(groupName, status);
    return ResponseEntity.ok(CARD_MAPPER.constructGetCards(cards));
  }

  @PatchMapping("/groups/{groupName}")
  public ResponseEntity<Status> updateCardInTheGroup(
      @RequestBody final CardRequest cardRequest, @PathVariable final String groupName) {
    Card constructedCard = CARD_MAPPER.constructCardRequestToUpdate(cardRequest);
    return ResponseEntity.ok(
        Status.builder()
            .status(requestCard.updateCardInTheGroup(constructedCard, groupName))
            .build());
  }

  @DeleteMapping("{cardName}/groups/{groupName}")
  public ResponseEntity<String> deleteCardInTheGroup(
      @PathVariable final String cardName, @PathVariable final String groupName) {
    Card constructedCard = Card.builder().name(cardName).build();
    return ResponseEntity.ok(requestCard.deleteCardInTheGroup(constructedCard, groupName));
  }
}
