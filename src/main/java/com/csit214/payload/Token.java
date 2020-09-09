package com.csit214.payload;

import java.time.LocalDateTime;

public class Token {
    private TokenType tokenType;
    private Long duration;
    private String accessToken;
    private LocalDateTime expiryDate;


    public Token(TokenType tokenType, Long duration, String accessToken, LocalDateTime expiryDate) {
        this.tokenType = tokenType;
        this.duration = duration;
        this.accessToken = accessToken;
        this.expiryDate = expiryDate;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
