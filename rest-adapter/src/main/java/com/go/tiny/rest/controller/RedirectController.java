package com.go.tiny.rest.controller;

import com.go.tiny.business.port.RequestCard;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

@RestController
@RequestMapping("/go.tiny/")
@CrossOrigin(origins = "*")
public class RedirectController {
  private RequestCard requestCard;

  public RedirectController(RequestCard requestCard) {
    this.requestCard = requestCard;
  }

  @GetMapping("{groupName}/{tinyUrl}")
  public ResponseEntity getActualUrl(@PathVariable String groupName, @PathVariable String tinyUrl) {
    String actualUrl = requestCard.getActualUrl(tinyUrl);
    return ResponseEntity.status(MOVED_PERMANENTLY).location(URI.create(actualUrl)).build();
  }
}
