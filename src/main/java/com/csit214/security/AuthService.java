package com.csit214.security;

import com.csit214.models.FrequentFlyerAccount;
import com.csit214.payload.AuthStatus;
import com.csit214.payload.JwtAuthenticationResponse;
import com.csit214.payload.LoginRequest;
import com.csit214.payload.Token;
import com.csit214.repository.UserRepository;
import com.csit214.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CookieUtil cookieUtil;

    public ResponseEntity<JwtAuthenticationResponse> login(LoginRequest loginRequest, String accessToken, String refreshToken, Authentication authentication) {
        String username = loginRequest.getUsername();
        FrequentFlyerAccount user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found with email " + username));

        Boolean accessTokenValid = tokenProvider.validateToken(accessToken);
        Boolean refreshTokenValid = tokenProvider.validateToken(refreshToken);
        System.out.println(accessTokenValid);
        System.out.println(refreshTokenValid);
        HttpHeaders responseHeaders = new HttpHeaders();
        Token newAccessToken;
        Token newRefreshToken;
        if (accessToken == null && refreshToken == null) {
            newAccessToken = tokenProvider.generateAccessToken(authentication);
            newRefreshToken = tokenProvider.generateRefreshToken(authentication);
            addAccessTokenCookie(responseHeaders, newAccessToken);
            addRefreshTokenCookie(responseHeaders, newRefreshToken);
        } else if (!accessTokenValid && !refreshTokenValid) {
            JwtAuthenticationResponse loginResponse = new JwtAuthenticationResponse(AuthStatus.FAILURE, "Session expired. Please Login Again.");
            return ResponseEntity.status(440).headers(responseHeaders).body(loginResponse);
        } else if (!accessTokenValid) {
            System.out.println("access token invald;");
            newAccessToken = tokenProvider.generateAccessToken(authentication);
            addAccessTokenCookie(responseHeaders, newAccessToken);
        } else if (refreshTokenValid) {
            newAccessToken = tokenProvider.generateAccessToken(authentication);
            newRefreshToken = tokenProvider.generateRefreshToken(authentication);
            addAccessTokenCookie(responseHeaders, newAccessToken);
            addRefreshTokenCookie(responseHeaders, newRefreshToken);
        }
        JwtAuthenticationResponse loginResponse = new JwtAuthenticationResponse(AuthStatus.SUCCESS, "Auth successful. Tokens are created in cookie.");
        return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
    }

    public ResponseEntity<JwtAuthenticationResponse> refresh(String accessToken, String refreshToken, Authentication authentication) {
        Boolean refreshTokenValid = tokenProvider.validateToken(refreshToken);
        if (!refreshTokenValid) {
            throw new IllegalArgumentException("Refresh Token is invalid!");
        }

        Token newAccessToken = tokenProvider.generateAccessToken(authentication);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createAccessTokenCookie(newAccessToken.getAccessToken(), newAccessToken.getDuration()).toString());

        JwtAuthenticationResponse loginResponse = new JwtAuthenticationResponse(AuthStatus.SUCCESS, "Auth successful. Tokens are created in cookie.");
        return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
    }

 /*   @Override
    public UserSummary getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = userRepository.findByEmail(customUserDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found with email " + customUserDetails.getUsername()));
        return user.toUserSummary();
    }*/

    private void addAccessTokenCookie(HttpHeaders httpHeaders, Token token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createAccessTokenCookie(token.getAccessToken(), token.getDuration()).toString());
    }

    private void addRefreshTokenCookie(HttpHeaders httpHeaders, Token token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createRefreshTokenCookie(token.getAccessToken(), token.getDuration()).toString());
    }
}
