package com.insurance.application.models;

import com.insurance.application.utils.ConvertDate;

import javax.persistence.*;
import java.text.ParseException;
import java.util.Objects;

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
    private String cubicCap;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private CarModel carModel;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    public Car() {
    }

    public int getId() {
        return id;
    }

    public String getRegDate() {
        return regDate;
    }

    public String getConvertedRegDate() {
        return ConvertDate.convertDate(regDate);
    }

    public String getCubicCap() {
        return cubicCap;
    }

    public CarModel getCarModel() {
        return carModel;
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

    public void setCubicCap(String cubicCap) {
        this.cubicCap = cubicCap;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, regDate, cubicCap);
    }
}
