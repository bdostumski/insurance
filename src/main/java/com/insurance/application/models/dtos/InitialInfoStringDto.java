package com.insurance.application.models.dtos;

public class InitialInfoStringDto {

    private String carBrand;

    private String carModel;

    private String carCubic;

    private String registrationDate;

    private String driverBirthDate;

    private String hasAccidents;

    private double totalPrice;

    private String tokenValue;



    public InitialInfoStringDto() {}

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

    public String getTokenValue() {
        return tokenValue;
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

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }
}
