package com.insurance.application.models;

import javax.persistence.*;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "policy_info")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "is_approval")
    private byte approval;

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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public byte getApproval() {
        return approval;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getStartTime() {
        return startTime;
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

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setApproval(byte approval) {
        this.approval = approval;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Policy policy = (Policy) o;
        return id == policy.id &&
                approval == policy.approval &&
                startDate.equals(policy.startDate) &&
                totalPrice.equals(policy.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, totalPrice, approval);
    }
}
