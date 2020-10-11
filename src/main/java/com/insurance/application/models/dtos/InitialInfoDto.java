package com.insurance.application.models.dtos;

import java.util.Date;

public class InitialInfoDto {

    String carBrand;

    String carModel;

    int carCubics;

    Date registrationDate;

    Date driverBirthDate;

    boolean hasAccidents;

    public InitialInfoDto() {
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getCarCubics() {
        return carCubics;
    }

    public void setCarCubics(int carCubics) {
        this.carCubics = carCubics;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getDriverBirthDate() {
        return driverBirthDate;
    }

    public void setDriverBirthDate(Date driverBirthDate) {
        this.driverBirthDate = driverBirthDate;
    }

    public boolean isHasAccidents() {
        return hasAccidents;
    }

    public void setHasAccidents(boolean hasAccidents) {
        this.hasAccidents = hasAccidents;
    }
}
