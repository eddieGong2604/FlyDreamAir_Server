package com.csit214.models;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Seating")
public class Seating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatingId;
    @ManyToOne
    private Flight flight;
    private double price;
    @Enumerated(EnumType.STRING)
    private SeatingClass seatingClass;
    private int availability;

    @OneToMany(mappedBy = "seating")
    private Set<Booking> bookingOfSeating;


    public Seating() {

    }

    public Seating(Flight flight, double price, SeatingClass seatingClass, int availability) {
        this.flight = flight;
        this.price = price;
        this.seatingClass = seatingClass;
        this.availability = availability;
    }

    public Set<Booking> getBookingOfSeating() {
        return bookingOfSeating;
    }

    public void setBookingOfSeating(Set<Booking> bookingOfSeating) {
        this.bookingOfSeating = bookingOfSeating;
    }

    public Long getSeatingId() {
        return seatingId;
    }

    public void setSeatingId(Long seatingId) {
        this.seatingId = seatingId;
    }

    public SeatingClass getSeatingClass() {
        return seatingClass;
    }

    public void setSeatingClass(SeatingClass seatingClass) {
        this.seatingClass = seatingClass;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }
}
