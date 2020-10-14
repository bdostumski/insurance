package com.insurance.application.models;

import javax.persistence.*;
import java.util.Objects;
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

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "password")
    private String password;

    @Column(name = "birthdate")
    private String birthdate;

    @Column(name = "prev_accident")
    private byte prevAccident;

    @Column(name = "enabled")
    private boolean enabled;


    @OneToMany(mappedBy = "userInfo")
    Set<Policy> policySet;

    @OneToMany(mappedBy = "userInfo")
    Set<Car> carSet;

    @ManyToOne
    @JoinColumn(name = "role_id")
    UserRole userRole;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private Token token;



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

    public String getPassword() {
        return password;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public byte getPrevAccident() {
        return prevAccident;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<Policy> getPolicySet() {
        return policySet;
    }

    public Set<Car> getCarSet() {
        return carSet;
    }

    public UserRole getUserRole() {
        return userRole;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrevAccident(byte prevAccident) {
        this.prevAccident = prevAccident;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setPolicySet(Set<Policy> policySet) {
        this.policySet = policySet;
    }

    public void setCarSet(Set<Car> carSet) {
        this.carSet = carSet;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return id == userInfo.id &&
                prevAccident == userInfo.prevAccident &&
                enabled == userInfo.enabled &&
                firstname.equals(userInfo.firstname) &&
                lastname.equals(userInfo.lastname) &&
                email.equals(userInfo.email) &&
                password.equals(userInfo.password) &&
                birthdate.equals(userInfo.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, email, password, birthdate, prevAccident, enabled);
    }
}
