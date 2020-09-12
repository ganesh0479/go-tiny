package com.go.tiny.rest.controller;

import com.go.tiny.business.model.CardGroup;
import com.go.tiny.business.port.RequestCardGroup;
import com.go.tiny.rest.model.CardGroupRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.go.tiny.rest.mapper.CardGroupMapper.CARD_GROUP_MAPPER;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/go-tiny/card-group")
public class CardGroupController {
  private RequestCardGroup requestCardGroup;

  public CardGroupController(RequestCardGroup requestCardGroup) {
    this.requestCardGroup = requestCardGroup;
  }

  @PostMapping
  public ResponseEntity<HttpStatus> add(@RequestBody final CardGroupRequest cardGroupRequest) {
    CardGroup cardGroup = CARD_GROUP_MAPPER.constructCardGroup(cardGroupRequest);
    requestCardGroup.add(cardGroup);
    return ResponseEntity.ok(OK);
  }
}
