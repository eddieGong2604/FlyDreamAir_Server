package com.csit214.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "FFAcount", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "passportNumber"
        })
})
public class FrequentFlyerAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    private String passportNumber;
    private String name;
    private String username;
    @JsonIgnore
    private String password;
    private double ffpoints;
    private double statusPoints;

    @Enumerated(EnumType.STRING)
    private FFType status;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Voucher> vouchers = new HashSet<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Booking> bookings = new HashSet<>();


    @OneToMany(mappedBy = "from", cascade = CascadeType.ALL)
    private Set<Enquiry> enquiries = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public FrequentFlyerAccount() {

    }

    public FrequentFlyerAccount(String passportNumber, String name, String username, String password, double ffpoints, FFType status, double statusPoints) {
        this.name = name;
        this.passportNumber = passportNumber;
        this.username = username;
        this.password = password;
        this.ffpoints = ffpoints;
        this.status = status;
        this.statusPoints = statusPoints;
    }


    public double getStatusPoints() {
        return statusPoints;
    }

    public void setStatusPoints(double statusPoints) {
        this.statusPoints = statusPoints;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public Set<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(Set<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getFfpoints() {
        return ffpoints;
    }

    public void setFfpoints(double ffpoints) {
        this.ffpoints = ffpoints;
    }

    public FFType getStatus() {
        return status;
    }

    public void setStatus(FFType status) {
        this.status = status;
    }

    public Set<Enquiry> getEnquiries() {
        return enquiries;
    }

    public void setEnquiries(Set<Enquiry> enquiries) {
        this.enquiries = enquiries;
    }
}
