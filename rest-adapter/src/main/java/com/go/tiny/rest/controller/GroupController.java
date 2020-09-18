package com.go.tiny.rest.controller;

import com.go.tiny.business.model.Group;
import com.go.tiny.business.port.RequestGroup;
import com.go.tiny.rest.model.GroupRequest;
import com.go.tiny.rest.model.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.go.tiny.rest.constant.GoTinyRestConstants.APPROVED;
import static com.go.tiny.rest.constant.GoTinyRestConstants.AUTHORIZED;
import static com.go.tiny.rest.mapper.GroupMapper.GROUP_MAPPER;

@RestController
@RequestMapping("/api/v1/go-tiny/groups")
@CrossOrigin(origins = "*")
public class GroupController {
  private RequestGroup requestGroup;

  public GroupController(RequestGroup requestGroup) {
    this.requestGroup = requestGroup;
  }

  @Operation(summary = "Create a group")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Created the group",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Status.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Unable to send request to create the group",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error. Unable to create group",
            content = @Content)
      })
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Status> create(@RequestBody final GroupRequest groupRequest) {
    Group group = GROUP_MAPPER.constructGroup(groupRequest);
    return ResponseEntity.ok(Status.builder().isGroupCreated(requestGroup.create(group)).build());
  }

  @Operation(summary = "Authorize card to display in the group")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Card is authorized to display in the group",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Status.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Unable to send card for authorization",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error. Unable to authorize cards",
            content = @Content)
      })
  @GetMapping("{groupName}/cards/{cardName}/authorize")
  public ResponseEntity<Status> authorizeCardToDisplayInGroup(
      @Parameter(description = "Group name of the card") @PathVariable String groupName,
      @Parameter(description = "Card name") @PathVariable String cardName) {
    requestGroup.authorizeCardToDisplayInGroup(groupName, cardName);
    return ResponseEntity.ok(Status.builder().status(AUTHORIZED).build());
  }

  @Operation(summary = "Send for approval of the card changes")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Approved card",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Status.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Unable to send to approval",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error. Unable to approve the cards",
            content = @Content)
      })
  @GetMapping("{groupName}/cards/{cardName}/approve")
  public ResponseEntity<Status> approveCardToDisplayInGroup(
      @Parameter(description = "Group name of the card") @PathVariable String groupName,
      @Parameter(description = "Card name") @PathVariable String cardName) {
    requestGroup.approveCardChangesInTheGroup(groupName, cardName);
    return ResponseEntity.ok(Status.builder().status(APPROVED).build());
  }
}
