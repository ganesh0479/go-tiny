package com.go.tiny.domain.model;

public record Card(String title, String description, String name, String actualUrl, Integer expiresIn, String createdBy, String tinyUrl) {
    public static String UNKNOWN;
    public static Integer UNKNOWN_INT=null;
    public Card(String tinyUrl, String name){
        this(UNKNOWN,UNKNOWN,name,UNKNOWN,UNKNOWN_INT,UNKNOWN,tinyUrl);
    }
}
