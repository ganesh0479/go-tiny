package com.go.tiny.business.model;

import com.go.tiny.business.constant.Role;

public record UserGroupRole(String groupName, Role role, String userName, String addedBy) {
}
