package com.csit214.models;

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
    private String password;
    private int ffpoints;

    @Enumerated(EnumType.STRING)
    private FFType status;
    private double statusDiscountPercent = 0.0;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Voucher> vouchers = new HashSet<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Booking> bookings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public FrequentFlyerAccount() {

    }

    public FrequentFlyerAccount(String passportNumber, String name, String username, String password, int ffpoints, FFType status, double statusDiscountPercent) {
        this.name = name;
        this.passportNumber = passportNumber;
        this.username = username;
        this.password = password;
        this.ffpoints = ffpoints;
        this.status = status;
        this.statusDiscountPercent = statusDiscountPercent;
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

    public int getFfpoints() {
        return ffpoints;
    }

    public void setFfpoints(int ffpoints) {
        this.ffpoints = ffpoints;
    }

    public FFType getStatus() {
        return status;
    }

    public void setStatus(FFType status) {
        this.status = status;
    }

    public double getStatusDiscountPercent() {
        return statusDiscountPercent;
    }

    public void setStatusDiscountPercent(double statusDiscountPercent) {
        this.statusDiscountPercent = statusDiscountPercent;
    }

}
