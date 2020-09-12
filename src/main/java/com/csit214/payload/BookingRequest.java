package com.csit214.payload;

public class BookingRequest {
    private Long seatingId;
    private String voucherCode;

    public BookingRequest() {

    }

    public BookingRequest(Long seatingId, String voucherCode) {
        this.seatingId = seatingId;
        this.voucherCode = voucherCode;
    }

    public Long getSeatingId() {
        return seatingId;
    }

    public String getVoucherCode() {
        return voucherCode;
    }
}
