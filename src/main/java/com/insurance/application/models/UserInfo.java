package com.insurance.application.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "birthdate")
    private String birthdate;

    @Column(name = "prev_accident")
    private byte prevAccident;

    @Column(name = "soft_delete")
    private byte accountStat;

    @OneToMany(mappedBy = "userInfo")
    Set<Policy> policySet;

    @OneToMany(mappedBy = "userInfo")
    Set<Car> carSet;

    public UserInfo() {}

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public byte getPrevAccident() {
        return prevAccident;
    }

    public byte getAccountStat() {
        return accountStat;
    }

    public Set<Policy> getPolicySet() {
        return policySet;
    }

    public Set<Car> getCarSet() {
        return carSet;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setPrevAccident(byte prevAccident) {
        this.prevAccident = prevAccident;
    }

    public void setAccountStat(byte softDelete) {
        this.accountStat = softDelete;
    }

    public void setPolicySet(Set<Policy> policySet) {
        this.policySet = policySet;
    }

    public void setCarSet(Set<Car> carSet) {
        this.carSet = carSet;
    }
}
