package com.csit214.models;


import javax.persistence.*;

@Entity
@Table(name = "Booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private double bookingPrice;

    @ManyToOne
    @JoinColumn(name = "seating_number")

    private Seating seating;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private FrequentFlyerAccount account;


    public Booking() {
    }

    public Booking(double bookingPrice) {
        this.bookingPrice = bookingPrice;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public double getBookingPrice() {
        return bookingPrice;
    }

    public void setBookingPrice(double bookingPrice) {
        this.bookingPrice = bookingPrice;
    }

    public Seating getSeating() {
        return seating;
    }

    public void setSeating(Seating seating) {
        this.seating = seating;
    }

    public FrequentFlyerAccount getAccount() {
        return account;
    }

    public void setAccount(FrequentFlyerAccount account) {
        this.account = account;
    }

}
