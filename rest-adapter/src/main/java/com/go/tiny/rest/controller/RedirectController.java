package com.go.tiny.rest.controller;

import com.go.tiny.business.model.CardGroup;
import com.go.tiny.business.port.RequestCardGroup;
import org.springframework.http.HttpStatus;
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

  @GetMapping("{groupName}/{tinyUrl}")
  public ResponseEntity<HttpStatus> getActualUrl(
      @PathVariable String groupName, @PathVariable String tinyUrl) {
    Optional<CardGroup> cardGroup = requestCardGroup.getActualUrl(groupName, tinyUrl);
    String actualUrl = cardGroup.map(CardGroup::getActualUrl).orElse(null);
    LocalDateTime createdOn = cardGroup.map(CardGroup::getCreatedTime).orElse(null);
    Integer expiresIn = cardGroup.map(CardGroup::getExpiresIn).orElse(null);
    LocalDateTime currentTime = LocalDateTime.now();
    if ("tiny".equals(groupName)
        && nonNull(createdOn)
        && currentTime.isBefore(createdOn.plusMinutes(expiresIn))) {
      return ResponseEntity.status(MOVED_PERMANENTLY).location(URI.create(actualUrl)).build();
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
  }
}
