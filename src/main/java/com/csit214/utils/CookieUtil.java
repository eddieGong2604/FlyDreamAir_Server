package com.csit214.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    @Value("${app.accessTokenCookieName}")
    private String accessTokenCookieName;

    @Value("${app.refreshTokenCookieName}")
    private String refreshTokenCookieName;

    public HttpCookie createAccessTokenCookie(String token, Long duration) {
        return ResponseCookie.from(accessTokenCookieName, token)
                .maxAge(duration)
                .httpOnly(true)
                .path("/")
                .build();
    }

    public HttpCookie createRefreshTokenCookie(String token, Long duration) {
        return ResponseCookie.from(refreshTokenCookieName, token)
                .maxAge(duration)
                .httpOnly(true)
                .path("/")
                .build();
    }

    public HttpCookie deleteAccessTokenCookie() {
        return ResponseCookie.from(accessTokenCookieName, "").maxAge(0).httpOnly(true).path("/").build();
    }

}