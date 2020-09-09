package com.csit214.models;

public enum FFType {
    PLATINUM(10.0),
    GOLD(10.0),
    SILVER(10.0);
    private double value;

    FFType(double value) {
        this.value = value;
    }


    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
