package com.go.tiny.rest.controller;

import com.go.tiny.business.model.CardGroup;
import com.go.tiny.business.port.RequestCardGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

@RestController
@RequestMapping("/go/")
@CrossOrigin(origins = "*")
public class RedirectController {
  private RequestCardGroup requestCardGroup;

  public RedirectController(RequestCardGroup requestCardGroup) {
    this.requestCardGroup = requestCardGroup;
  }

  @Operation(summary = "Get actual url from tiny url")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found actual url",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = HttpStatus.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Unable to find the actual url",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error. Unable to find actual url",
            content = @Content)
      })
  @GetMapping(value = "{groupName}/{tinyUrl}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity getActualUrl(
      @Parameter(description = "Group name of the user") @PathVariable String groupName,
      @Parameter(description = "Tiny url") @PathVariable String tinyUrl) {
    Optional<CardGroup> cardGroup = requestCardGroup.getActualUrl(groupName, tinyUrl);
    String actualUrl = cardGroup.map(CardGroup::getActualUrl).orElse(null);
    LocalDateTime createdOn = cardGroup.map(CardGroup::getCreatedTime).orElse(null);
    Integer expiresIn = cardGroup.map(CardGroup::getExpiresIn).orElse(null);
    LocalDateTime currentTime = LocalDateTime.now();
    if ("tiny".equals(groupName)
        && nonNull(createdOn)
        && currentTime.isBefore(createdOn.plusMinutes(expiresIn))) {
      String urlToRedirect = actualUrl.startsWith("http") ? actualUrl : "http://" + actualUrl;
      return ResponseEntity.status(MOVED_PERMANENTLY).location(URI.create(urlToRedirect)).build();
    } else if (nonNull(groupName) && !"tiny".equals(groupName)) {
      String urlToRedirect = actualUrl.startsWith("http") ? actualUrl : "http://" + actualUrl;
      return ResponseEntity.status(MOVED_PERMANENTLY).location(URI.create(urlToRedirect)).build();
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
  }
}
