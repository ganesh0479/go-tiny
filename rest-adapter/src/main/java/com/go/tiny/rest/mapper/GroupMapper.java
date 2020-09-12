package com.go.tiny.rest.mapper;

import com.go.tiny.business.model.Group;
import com.go.tiny.rest.model.GroupRequest;

public enum GroupMapper {
  GROUP_MAPPER;

  public Group constructGroup(final GroupRequest groupRequest) {
    return Group.builder()
        .name(groupRequest.getName())
        .createdBy(groupRequest.getCreatedBy())
        .build();
  }
}
