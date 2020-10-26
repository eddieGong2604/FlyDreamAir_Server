package com.csit214.payload;

public class AdminRequest {
    private String email;

    public AdminRequest(){

    }
    public AdminRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
