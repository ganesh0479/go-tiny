package com.go.tiny.business.model;

import java.time.LocalDate;
import java.util.Objects;

public record User(String name, String emailId, String password, LocalDate createdOn) {
    public User {
        Objects.requireNonNull(emailId);
        Objects.requireNonNull(password);
    }
    public User(String emailId,String password){
        this(null,emailId,password,null);
    }
}
