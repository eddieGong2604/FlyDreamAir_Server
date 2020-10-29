package com.csit214.payload;

import com.csit214.models.Booking;
import com.csit214.models.FFType;

public class BookingInfoResponse {
    private Booking booking;
    private double initialPrice;
    private String voucherCode;
    private double afterPrice;
    private double statusPointsAdded;
    private double ffpointsAdded;

    public BookingInfoResponse() {
    }

    public BookingInfoResponse(Booking booking, double initialPrice, String voucherCode, double afterPrice, double statusPointsAdded, double ffpointsAdded) {
        this.booking = booking;
        this.initialPrice = initialPrice;
        this.voucherCode = voucherCode;
        this.afterPrice = afterPrice;
        this.statusPointsAdded = statusPointsAdded;
        this.ffpointsAdded = ffpointsAdded;
    }


    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public double getAfterPrice() {
        return afterPrice;
    }

    public void setAfterPrice(double afterPrice) {
        this.afterPrice = afterPrice;
    }

    public double getStatusPointsAdded() {
        return statusPointsAdded;
    }

    public void setStatusPointsAdded(double statusPointsAdded) {
        this.statusPointsAdded = statusPointsAdded;
    }

    public double getFfpointsAdded() {
        return ffpointsAdded;
    }

    public void setFfpointsAdded(double ffpointsAdded) {
        this.ffpointsAdded = ffpointsAdded;
    }
}
