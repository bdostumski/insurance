package com.insurance.application.models;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "coefficient")
public class Coefficient {

    @Column(name = "accident")
    private Double accident;

    @Column(name = "age_limit")
    private int ageLimit;

    @Column(name = "driver_age")
    private int driverAge;

    @Column(name = "tax_amount")
    private double taxAmount;

    public Coefficient() {}

    public Double getAccident() {
        return accident;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public int getDriverAge() {
        return driverAge;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setAccident(Double accident) { this.accident = accident; }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public void setDriverAge(int driverAge) {
        this.driverAge = driverAge;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }
}

