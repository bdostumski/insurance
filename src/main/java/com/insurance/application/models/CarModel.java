package com.insurance.application.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "car_model")
public class CarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "model")
    private String model;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private CarBrand carBrand;

    @JsonIgnore
    @OneToMany(mappedBy = "carModel", fetch = FetchType.EAGER)
    private Set<Car> cars;

    public CarModel() {}

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public Set<Car> getCars() {
        return cars;
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

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    public void setCarBrand(CarBrand carBrand) {
        this.carBrand = carBrand;
    }
}
