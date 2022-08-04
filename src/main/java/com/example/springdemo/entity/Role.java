package com.example.springdemo.entity;

public enum Role {
    USER("User is default"), ADMIN("User is admin");
    private final String description;
    Role(String description){
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
