package com.csit214.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Voucher")

public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voucherId;
    private String voucherCode;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private FrequentFlyerAccount account;

    private double discount;
    private LocalDateTime validDate;
    private boolean isValid;

    public Voucher() {

    }

    public Voucher(String voucherCode, FrequentFlyerAccount account, double discount, LocalDateTime validDate, boolean isValid) {
        this.voucherCode = voucherCode;
        this.account = account;
        this.discount = discount;
        this.validDate = validDate;
        this.isValid = isValid;
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

    public LocalDateTime getValidDate() {
        return validDate;
    }

    public void setValidDate(LocalDateTime validDate) {
        this.validDate = validDate;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
