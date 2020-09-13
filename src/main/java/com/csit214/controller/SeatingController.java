package com.csit214.controller;

import com.csit214.models.Flight;
import com.csit214.models.Seating;
import com.csit214.repository.FlightRepository;
import com.csit214.repository.SeatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("api/seating")
public class SeatingController {
    @Autowired
    private SeatingRepository seatingRepository;

    @Autowired
    private FlightRepository flightRepository;

    @GetMapping("/flightId={flightId}")
    @PreAuthorize("hasRole('USER')")
    public Set<Seating> getFlightSeating(@PathVariable Long flightId) {
        return flightRepository.findById(flightId).orElse(null).getSeatingSet();
    }

    @GetMapping("/seatingId={seatingId}")
    @PreAuthorize("hasRole('USER')")
    public Seating getSeatingById(@PathVariable Long seatingId) {
        return seatingRepository.findById(seatingId).orElse(null);
    }
}
