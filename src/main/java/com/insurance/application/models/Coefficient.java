package com.insurance.application.models;

import javax.persistence.*;

@Entity
@Table(name = "coefficient")
public class Coefficient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "accident")
    private Double accident;

    @Column(name = "age_limit")
    private int ageLimit;

    @Column(name = "age_coefficient")
    private double ageCoefficient;

    @Column(name = "tax_amount")
    private double taxAmount;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getAccident() {
        return accident;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public double getAgeCoefficient() {
        return ageCoefficient;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccident(Double accident) {
        this.accident = accident;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public void setAgeCoefficient(double ageCoefficient) {
        this.ageCoefficient = ageCoefficient;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }
}

