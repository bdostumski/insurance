package com.insurance.application.models;

import javax.persistence.*;

@Entity
@Table(name = "car_model")
public class CarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "model")
    private String model;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private CarBrand carBrand;

    public CarModel() {}

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public CarBrand getCarBrand() {
        return carBrand;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setCarBrand(CarBrand carBrand) {
        this.carBrand = carBrand;
    }
}
