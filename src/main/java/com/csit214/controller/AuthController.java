package com.csit214.controller;


import com.csit214.exception.AppException;
import com.csit214.models.FFType;
import com.csit214.models.FrequentFlyerAccount;
import com.csit214.models.Role;
import com.csit214.models.RoleName;
import com.csit214.payload.ApiResponse;
import com.csit214.payload.LoginRequest;
import com.csit214.payload.SignUpRequest;
import com.csit214.repository.RoleRepository;
import com.csit214.repository.UserRepository;
import com.csit214.security.AuthService;
import com.csit214.security.JwtTokenProvider;
import com.csit214.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    CookieUtil cookieUtil;

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@CookieValue(name = "accessToken", required = false) String accessToken,
                                              @CookieValue(name = "refreshToken", required = false) String refreshToken
            , @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authService.login(loginRequest, accessToken, refreshToken, authentication);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findByPassportNumber(signUpRequest.getPassportNumber()).isPresent()) {
            return new ResponseEntity(new ApiResponse(false, "Passport number already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        //    public FrequentFlyerAccount(String passportNumber, String name, String username, String password, int ffpoints, FFType status, double statusDiscountPercent) {
        FrequentFlyerAccount user = new FrequentFlyerAccount(signUpRequest.getPassportNumber(), signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getPassword(), 0, FFType.SILVER, FFType.SILVER.getValue());
        //still hard coded

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        FrequentFlyerAccount result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @PostMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> refreshToken(@CookieValue(name = "accessToken", required = false) String accessToken,
                                          @CookieValue(name = "refreshToken", required = false) String refreshToken) {

        return authService.refresh(accessToken, refreshToken, SecurityContextHolder.getContext().getAuthentication());
    }
}