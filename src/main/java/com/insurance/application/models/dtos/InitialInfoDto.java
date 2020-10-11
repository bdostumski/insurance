package com.insurance.application.models.dtos;

import java.util.Date;

public class InitialInfoDto {

    int carBrand;

    int carModel;

    int carCubics;

    Date registrationDate;

    Date driverBirthDate;

    boolean hasAccidents;

    public InitialInfoDto() {
    }

    public int getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(int carBrand) {
        this.carBrand = carBrand;
    }

    public int getCarModel() {
        return carModel;
    }

    public void setCarModel(int carModel) {
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
