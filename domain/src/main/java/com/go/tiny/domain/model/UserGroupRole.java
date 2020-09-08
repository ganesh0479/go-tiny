package com.go.tiny.domain.model;

import com.go.tiny.domain.constant.Role;

public record UserGroupRole(String groupName, Role role, String userName, String addedBy) {
}
