package com.go.tiny.rest.controller;

import com.go.tiny.business.model.Group;
import com.go.tiny.business.port.RequestGroup;
import com.go.tiny.rest.model.GroupRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.go.tiny.rest.constant.GoTinyRestConstants.APPROVED;
import static com.go.tiny.rest.constant.GoTinyRestConstants.AUTHORIZED;
import static com.go.tiny.rest.mapper.GroupMapper.GROUP_MAPPER;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/go-tiny/groups")
public class GroupController {
  private RequestGroup requestGroup;

  public GroupController(RequestGroup requestGroup) {
    this.requestGroup = requestGroup;
  }

  @PostMapping
  public ResponseEntity<HttpStatus> create(@RequestBody final GroupRequest groupRequest) {
    Group group = GROUP_MAPPER.constructGroup(groupRequest);
    requestGroup.create(group);
    return ResponseEntity.ok(OK);
  }

  @GetMapping("{groupName}/cards/{cardName}/authorize")
  public ResponseEntity<String> authorizeCardToDisplayInGroup(
      @PathVariable String groupName, @PathVariable String cardName) {
    requestGroup.authorizeCardToDisplayInGroup(groupName, cardName);
    return ResponseEntity.ok(AUTHORIZED);
  }

  @GetMapping("{groupName}/cards/{cardName}/approve")
  public ResponseEntity<String> approveCardToDisplayInGroup(
      @PathVariable String groupName, @PathVariable String cardName) {
    requestGroup.approveCardChangesInTheGroup(groupName, cardName);
    return ResponseEntity.ok(APPROVED);
  }
}
