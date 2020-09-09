package com.csit214.models;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Flight")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;
    private String flightNumber;
    private String airlineName;
    private String departAirport;
    private LocalDateTime departTime;
    private String arriveAirport;
    private LocalDateTime arriveTime;

    public Flight() {
    }

    public Flight(String flightNumber, String airlineName, String departAirport, LocalDateTime departTime, String arriveAirport, LocalDateTime arriveTime) {
        this.flightNumber = flightNumber;
        this.airlineName = airlineName;
        this.departAirport = departAirport;
        this.departTime = departTime;
        this.arriveAirport = arriveAirport;
        this.arriveTime = arriveTime;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getDepartAirport() {
        return departAirport;
    }

    public void setDepartAirport(String departAirport) {
        this.departAirport = departAirport;
    }

    public LocalDateTime getDepartTime() {
        return departTime;
    }

    public void setDepartTime(LocalDateTime departTime) {
        this.departTime = departTime;
    }

    public String getArriveAirport() {
        return arriveAirport;
    }

    public void setArriveAirport(String arriveAirport) {
        this.arriveAirport = arriveAirport;
    }

    public LocalDateTime getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(LocalDateTime arriveTime) {
        this.arriveTime = arriveTime;
    }
}
