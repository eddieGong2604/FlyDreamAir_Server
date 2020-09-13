package com.csit214.controller;

import com.csit214.models.Booking;
import com.csit214.models.FrequentFlyerAccount;
import com.csit214.repository.UserRepository;
import com.csit214.security.CurrentUser;
import com.csit214.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/info")
    @PreAuthorize("hasRole('USER')")

    public FrequentFlyerAccount getUser(@CurrentUser UserPrincipal currentUser) {
        return userRepository.findById(currentUser.getId()).orElse(null);
    }

}
