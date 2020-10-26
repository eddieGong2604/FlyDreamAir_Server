package com.csit214.controller;

import com.csit214.models.Booking;
import com.csit214.models.FrequentFlyerAccount;
import com.csit214.repository.UserRepository;
import com.csit214.security.CurrentUser;
import com.csit214.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/info")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public FrequentFlyerAccount getUser(@CurrentUser UserPrincipal currentUser) {
        return userRepository.findById(currentUser.getId()).orElse(null);
    }

    @GetMapping("/allUser")
    @PreAuthorize("hasRole('ADMIN')")
    public List<FrequentFlyerAccount> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public FrequentFlyerAccount getUser(@PathVariable Long id){

        return userRepository.findById(id).orElse(null);
    }



}
