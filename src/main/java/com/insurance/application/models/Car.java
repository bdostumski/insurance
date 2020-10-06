package com.insurance.application.models;

import javax.persistence.*;

@Entity
@Table(name = "car_info")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "reg_date")
    private String regDate;

    @Column(name = "cubic_cap")
    private double cubicCap;

    @OneToOne
    @JoinColumn(name = "brand_id")
    private CarBrand carBrand;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    public Car() {}

    public int getId() {
        return id;
    }

    public String getRegDate() {
        return regDate;
    }

    public double getCubicCap() {
        return cubicCap;
    }

    public CarBrand getCarBrand() {
        return carBrand;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public void setCubicCap(double cubicCap) {
        this.cubicCap = cubicCap;
    }

    public void setCarBrand(CarBrand carBrand) {
        this.carBrand = carBrand;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
