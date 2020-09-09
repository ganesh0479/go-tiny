package com.go.tiny.business.model;

import java.util.Objects;

public record Group(String name, String createdBy) {
    public Group {
        Objects.requireNonNull(name);
    }
}
