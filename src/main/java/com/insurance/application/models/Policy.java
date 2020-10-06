package com.insurance.application.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "policy_info")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "start_date_time")
    private String startDateTime;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "is_approval")
    private byte isApproval;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @OneToMany(mappedBy = "policy", fetch = FetchType.EAGER)
    private Set<Image> imageSet;

    public Policy() {}

    public long getId() {
        return id;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public byte getIsApproval() {
        return isApproval;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public Car getCar() {
        return car;
    }

    public Set<Image> getImageSet() {
        return imageSet;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setIsApproval(byte isApproval) {
        this.isApproval = isApproval;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setImageSet(Set<Image> imageSet) {
        this.imageSet = imageSet;
    }
}
