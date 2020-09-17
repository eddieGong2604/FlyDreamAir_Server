package com.csit214.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Voucher",uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "voucherCode"
        })
})

public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voucherId;
    private String voucherCode;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private FrequentFlyerAccount account;

    private double discount;
    private boolean isValid;

    @OneToOne(mappedBy = "voucher")
    @JsonIgnore
    private Booking booking;

    public Voucher() {

    }

    public Voucher(String voucherCode, double discount, boolean isValid) {
        this.voucherCode = voucherCode;
        this.discount = discount;
        this.isValid = isValid;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public FrequentFlyerAccount getAccount() {
        return account;
    }

    public void setAccount(FrequentFlyerAccount account) {
        this.account = account;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
