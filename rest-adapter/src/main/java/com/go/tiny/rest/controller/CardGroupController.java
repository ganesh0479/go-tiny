package com.go.tiny.rest.controller;

import com.go.tiny.business.model.CardGroup;
import com.go.tiny.business.port.RequestCardGroup;
import com.go.tiny.rest.model.CardGroupRequest;
import com.go.tiny.rest.model.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.go.tiny.rest.mapper.CardGroupMapper.CARD_GROUP_MAPPER;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/go-tiny/card-group")
@CrossOrigin(origins = "*")
public class CardGroupController {
  private RequestCardGroup requestCardGroup;

  public CardGroupController(RequestCardGroup requestCardGroup) {
    this.requestCardGroup = requestCardGroup;
  }

  @Operation(summary = "Assign the card to group")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Assigned card to group",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Status.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Unable to send request to assign card to group",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error. Unable to assign card to group",
            content = @Content)
      })
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Status> add(@RequestBody final CardGroupRequest cardGroupRequest) {
    CardGroup cardGroup = CARD_GROUP_MAPPER.constructCardGroup(cardGroupRequest);
    requestCardGroup.add(cardGroup);
    return ResponseEntity.ok(Status.builder().status(OK.toString()).build());
  }
}
