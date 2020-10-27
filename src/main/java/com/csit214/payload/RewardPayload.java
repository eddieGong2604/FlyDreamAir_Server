package com.csit214.payload;

public class RewardPayload {
    private String username;
    private double value;

    public RewardPayload() {
    }

    public RewardPayload(String username, double value) {
        this.username = username;
        this.value = value;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
