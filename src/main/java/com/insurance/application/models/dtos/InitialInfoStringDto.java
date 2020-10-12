package com.insurance.application.models.dtos;

import javax.persistence.*;

@Entity
@Table(name = "info_dto")
public class InitialInfoStringDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "car_brand")
    private String carBrand;

    @Column(name = "car_model")
    private String carModel;

    @Column(name = "car_cubic")
    private String carCubic;

    @Column(name = "registration_date")
    private String registrationDate;

    @Column(name = "driver_birthdate")
    private String driverBirthDate;

    @Column(name = "accident")
    private String hasAccidents;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "user_token")
    private String userToken;

    public InitialInfoStringDto() {}

    public int getId() {
        return id;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getCarCubic() {
        return carCubic;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public String getDriverBirthDate() {
        return driverBirthDate;
    }

    public String getHasAccidents() {
        return hasAccidents;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setCarCubic(String carCubic) {
        this.carCubic = carCubic;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setDriverBirthDate(String driverBirthDate) {
        this.driverBirthDate = driverBirthDate;
    }

    public void setHasAccidents(String hasAccidents) {
        this.hasAccidents = hasAccidents;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
