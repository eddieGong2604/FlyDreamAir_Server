package com.csit214.controller;

import com.csit214.models.Flight;
import com.csit214.models.FrequentFlyerAccount;
import com.csit214.repository.FlightRepository;
import com.csit214.repository.UserRepository;
import com.csit214.security.CurrentUser;
import com.csit214.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/flight")
public class FlightController {
    @Autowired
    private FlightRepository flightRepository;

    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public List<Flight> getFlights() {
        return flightRepository.findAll();
    }

    @GetMapping("/flightId={flightId}")
    @PreAuthorize("hasRole('USER')")
    public Flight getFlightByFlightId(@PathVariable Long flightId) {
        return flightRepository.findById(flightId).orElse(null);
    }

}
