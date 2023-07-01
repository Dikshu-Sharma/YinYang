package com.soulharmony.model;


public class UserFilter {
    private String userId;

    public UserFilter(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
