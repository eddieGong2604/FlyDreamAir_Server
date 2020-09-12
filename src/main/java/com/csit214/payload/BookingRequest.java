package com.csit214.payload;

public class BookingRequest {
    private Long seatingId;
    private Long voucherId;

    public BookingRequest() {

    }

    public BookingRequest(Long seatingId, Long voucherId) {
        this.seatingId = seatingId;
        this.voucherId = voucherId;
    }

    public Long getSeatingId() {
        return seatingId;
    }

    public Long getVoucherId() {
        return voucherId;
    }
}
