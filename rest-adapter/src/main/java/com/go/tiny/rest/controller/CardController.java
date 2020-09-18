package com.go.tiny.rest.controller;

import com.go.tiny.business.model.Card;
import com.go.tiny.business.port.RequestCard;
import com.go.tiny.rest.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.go.tiny.rest.mapper.CardMapper.CARD_MAPPER;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/go-tiny/cards")
@CrossOrigin(origins = "*")
public class CardController {
  @Value("${go.tiny.server}")
  private String serverAddress;

  private RequestCard requestCard;

  public CardController(RequestCard requestCard) {
    this.requestCard = requestCard;
  }

  @Operation(summary = "Create card to share")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Create the card",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = CreateCardResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Unable to send request to create card",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error. Unable to create card",
            content = @Content)
      })
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CreateCardResponse> create(@RequestBody final CardRequest cardRequest) {
    Card card = CARD_MAPPER.constructCardRequest(cardRequest);
    Card createdCard = requestCard.create(card);
    return ResponseEntity.ok(
        CARD_MAPPER.constructCreateCardResponse(
            createdCard.toBuilder()
                .tinyUrl(serverAddress + "/go/tiny/" + createdCard.getTinyUrl())
                .build()));
  }

  @Operation(summary = "Update some part of the card")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Updated the card",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = Status.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Unable to send request to update card",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error. Unable to update card",
            content = @Content)
      })
  @PatchMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Status> update(@RequestBody final CardRequest cardRequest) {
    Card card = CARD_MAPPER.constructCardRequestToUpdate(cardRequest);
    requestCard.update(card);
    return ResponseEntity.ok(Status.builder().status(OK.toString()).build());
  }

  @Operation(summary = "Delete card by name")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Deleted the card",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = Status.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Unable to send request to delete card",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error. Unable to delete card",
            content = @Content)
      })
  @DeleteMapping("{cardName}")
  public ResponseEntity<Status> delete(
      @Parameter(description = "Card name") @PathVariable final String cardName) {
    requestCard.delete(cardName);
    return ResponseEntity.ok(Status.builder().status(OK.toString()).build());
  }

  @Operation(summary = "Get card by name")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Fetched card",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = GetCardResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Unable to send request to fetch card",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error. Unable to fetch card",
            content = @Content)
      })
  @GetMapping("{cardName}")
  public ResponseEntity<GetCardResponse> get(
      @Parameter(description = "Card name") @PathVariable final String cardName) {
    Card requestedCard = requestCard.get(cardName);
    return ResponseEntity.ok(CARD_MAPPER.constructGetCardResponse(requestedCard, serverAddress));
  }

  @Operation(summary = "Get cards not belongs to the group")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Fetched cards not belongs to the group",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = GetCards.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Unable to send request to fetch cards",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error. Unable to fetch cards",
            content = @Content)
      })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GetCards> getAll() {
    List<Card> cards = requestCard.getCardsNotBelongToGroup();
    return ResponseEntity.ok(CARD_MAPPER.constructGetCards(cards, serverAddress));
  }

  @Operation(summary = "Get cards belongs to the group")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Fetched cards belongs to the group",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = GetCards.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Unable to send request to fetch cards",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error. Unable to fetch cards",
            content = @Content)
      })
  @GetMapping(value = "/groups/{groupName}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GetCards> getCardsBelongToGroup(
      @Parameter(description = "Group name") @PathVariable final String groupName) {
    List<Card> cards = requestCard.getCardsBelongToGroup(groupName);
    return ResponseEntity.ok(CARD_MAPPER.constructGetCards(cards, serverAddress));
  }

  @Operation(summary = "Get cards belongs to the group by status")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Fetched cards belongs to the group",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = GetCards.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Unable to send request to fetch cards",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error. Unable to fetch cards",
            content = @Content)
      })
  @GetMapping(value = "{status}/groups/{groupName}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GetCards> getCardsBelongToGroupByStatus(
      @Parameter(description = "Status of the card") @PathVariable final String status,
      @Parameter(description = "Group name of the card") @PathVariable final String groupName) {
    List<Card> cards = requestCard.getCardsBelongToGroupByStatus(groupName, status);
    return ResponseEntity.ok(CARD_MAPPER.constructGetCards(cards, serverAddress));
  }

  @Operation(summary = "Update the card in the group and send for approval")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Sent for approval",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = Status.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Unable to send request to approve",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error. Unable to approve requested changes",
            content = @Content)
      })
  @PatchMapping("/groups/{groupName}")
  public ResponseEntity<Status> updateCardInTheGroup(
      @RequestBody final CardRequest cardRequest,
      @Parameter(description = "Group name of the card") @PathVariable final String groupName) {
    Card constructedCard = CARD_MAPPER.constructCardRequestToUpdate(cardRequest);
    return ResponseEntity.ok(
        Status.builder()
            .status(requestCard.updateCardInTheGroup(constructedCard, groupName))
            .build());
  }

  @Operation(summary = "Delete the card in the group and send for approval")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Sent for approval",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = Status.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Unable to send request to delete teh card",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error. Unable to delete the card",
            content = @Content)
      })
  @DeleteMapping("{cardName}/groups/{groupName}")
  public ResponseEntity<Status> deleteCardInTheGroup(
      @Parameter(description = "Card name") @PathVariable final String cardName,
      @Parameter(description = "Group name") @PathVariable final String groupName) {
    Card constructedCard = Card.builder().name(cardName).build();
    return ResponseEntity.ok(
        Status.builder()
            .status(requestCard.deleteCardInTheGroup(constructedCard, groupName))
            .build());
  }

  @Operation(summary = "Update an avatar for the card")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Updated card avatar",
            content = {
              @Content(
                  mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                  schema = @Schema(implementation = HttpStatus.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Unable to send request to update avatar",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error. Unable to update avatar",
            content = @Content)
      })
  @PostMapping(value = "{cardName}/avatar", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Status> uploadImage(
      @Parameter(description = "Card name") @PathVariable String cardName,
      @RequestParam("imageFile") MultipartFile imageFile) {
    byte[] fileData = CARD_MAPPER.compressBytes(imageFile);
    return ResponseEntity.ok(
        Status.builder().status(this.requestCard.uploadAvatar(fileData, cardName)).build());
  }
}
