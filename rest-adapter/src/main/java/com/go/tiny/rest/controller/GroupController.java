package com.go.tiny.rest.controller;

import com.go.tiny.business.model.Group;
import com.go.tiny.business.port.RequestGroup;
import com.go.tiny.rest.model.GroupRequest;
import com.go.tiny.rest.model.Status;
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

  @PostMapping
  public ResponseEntity<Status> create(@RequestBody final GroupRequest groupRequest) {
    Group group = GROUP_MAPPER.constructGroup(groupRequest);
    return ResponseEntity.ok(Status.builder().isGroupCreated(requestGroup.create(group)).build());
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
