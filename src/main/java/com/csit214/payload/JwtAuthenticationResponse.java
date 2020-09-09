package com.csit214.payload;

public class JwtAuthenticationResponse {
    private AuthStatus accessToken;
    private String message;

    public JwtAuthenticationResponse(AuthStatus accessToken, String message) {
        this.accessToken = accessToken;
        this.message = message;
    }

    public AuthStatus getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AuthStatus accessToken) {
        this.accessToken = accessToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
