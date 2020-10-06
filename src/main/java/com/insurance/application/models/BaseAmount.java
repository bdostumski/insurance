package com.insurance.application.models;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "base_amount_details")
public class BaseAmount {

    @Column(name = "cc_min")
    private int ccMin;

    @Column(name = "cc_max")
    private int ccMax;

    @Column(name = "car_age_min")
    private int carAgeMin;

    @Column(name = "car_age_max")
    private int carAgeMax;

    @Column(name = "base_amount")
    private double baseAmount;

    public BaseAmount(){}

    public int getCcMin() {
        return ccMin;
    }

    public int getCcMax() {
        return ccMax;
    }

    public int getCarAgeMin() {
        return carAgeMin;
    }

    public int getCarAgeMax() {
        return carAgeMax;
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    public void setCcMin(int ccMin) {
        this.ccMin = ccMin;
    }

    public void setCcMax(int ccMax) {
        this.ccMax = ccMax;
    }

    public void setCarAgeMin(int carAgeMin) {
        this.carAgeMin = carAgeMin;
    }

    public void setCarAgeMax(int carAgeMax) {
        this.carAgeMax = carAgeMax;
    }

    public void setBaseAmount(double baseAmount) {
        this.baseAmount = baseAmount;
    }
}
