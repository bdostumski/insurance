package com.insurance.application.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "car_brand")
public class CarBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "brand")
    private String brand;

    @JsonIgnore
    @OneToMany(mappedBy = "carBrand", fetch = FetchType.EAGER)
    private Set<CarModel> carModelSet;

    public CarBrand() {}

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public Set<CarModel> getCarModelSet() {
        return carModelSet;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCarModelSet(Set<CarModel> carModelSet) {
        this.carModelSet = carModelSet;
    }
}
