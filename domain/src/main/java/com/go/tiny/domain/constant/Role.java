package com.go.tiny.domain.constant;

public enum Role {
  ADMIN("ADMIN"),
  USER("USER");
  private String role;

  Role(String role) {
    this.role = role;
  }
}
