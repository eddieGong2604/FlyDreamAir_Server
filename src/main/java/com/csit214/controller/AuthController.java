package com.csit214.controller;


import com.csit214.exception.AppException;
import com.csit214.models.FFType;
import com.csit214.models.FrequentFlyerAccount;
import com.csit214.models.Role;
import com.csit214.models.RoleName;
import com.csit214.payload.AdminRequest;
import com.csit214.payload.ApiResponse;
import com.csit214.payload.LoginRequest;
import com.csit214.payload.SignUpRequest;
import com.csit214.repository.RoleRepository;
import com.csit214.repository.UserRepository;
import com.csit214.security.AuthService;
import com.csit214.security.CurrentUser;
import com.csit214.security.JwtTokenProvider;
import com.csit214.security.UserPrincipal;
import com.csit214.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    JavaMailSender javaMailSender;
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

    @GetMapping("/is_logged_in")
    public ResponseEntity<?> checkIfLoggedIn(@CurrentUser UserPrincipal userPrincipal) {
        if (userPrincipal == null) {
            return new ResponseEntity(new ApiResponse(false, "Not logged In"),
                    HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(new ApiResponse(true, "logged in"),
                    HttpStatus.ACCEPTED);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.ACCEPTED);
        }

        if (userRepository.findByPassportNumber(signUpRequest.getPassportNumber()).isPresent()) {
            return new ResponseEntity(new ApiResponse(false, "Passport number already in use!"),
                    HttpStatus.ACCEPTED);
        }

        // Creating user's account
        //    public FrequentFlyerAccount(String passportNumber, String name, String username, String password, int ffpoints, FFType status, double statusDiscountPercent) {
        FrequentFlyerAccount user = new FrequentFlyerAccount(signUpRequest.getPassportNumber(), signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getPassword(), 0, FFType.SILVER, 0);
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

    @PostMapping("/signupadmin")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminRequest adminRequest) throws MessagingException, UnsupportedEncodingException {
        String email = adminRequest.getEmail();
        System.out.println(email);
        if (userRepository.existsByUsername(email)) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.ACCEPTED);
        }
        String passwordPlain = "admin" + adminRequest.getName().replaceAll(" ", ""); ;

        FrequentFlyerAccount user = new FrequentFlyerAccount("000" + userRepository.findAll().size(), adminRequest.getName(), email,
                passwordPlain, 0, FFType.SILVER, 0);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new AppException("User Role not set."));
        user.setRoles(Collections.singleton(userRole));

        //save admin to database
        //send email to finalize admin registration
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        String mailSubject = "FlyDreamAir Admin Registration";
        String mailContent = "<p> Thank you for your registration to be an admin of FlyDreamAir </p>" +
                "<p>Here is the credentials to access the system admin dashboard </p>" +
                "<p>Email: <b>" + email + "</b> </p>" + "<p>Password: <b>" + passwordPlain + "</b> </p>";
        helper.setFrom("flydreamairaustralia@gmail.com", "FlyDreamAir");
        System.out.println(email);
        helper.setTo(email);
        helper.setSubject(mailSubject);
        helper.setText(mailContent, true);

        try {
            javaMailSender.send(message);
            userRepository.save(user);

        } catch (MailSendException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid email"));

        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(user.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User Admin registered successfully. Check your email for the password"));

    }


    @PostMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> refreshToken(@CookieValue(name = "accessToken", required = false) String accessToken,
                                          @CookieValue(name = "refreshToken", required = false) String refreshToken) {

        return authService.refresh(accessToken, refreshToken, SecurityContextHolder.getContext().getAuthentication());
    }

    @PostMapping(value = "/logout")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> logout() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.deleteAccessTokenCookie().toString());
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.deleteRefreshTokenCookie().toString());

        return ResponseEntity.ok().headers(httpHeaders).body(new ApiResponse(true, "Log out successful"));
    }
}