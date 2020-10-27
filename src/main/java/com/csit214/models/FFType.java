package com.csit214.models;

public enum FFType {
    PLATINUM(60.0,100),
    GOLD(40.0,50),
    SILVER(10.0,0);
    private double value;
    private double threshold;

    FFType(double value,double threshold) {
        this.value = value;
        this.threshold = threshold;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
