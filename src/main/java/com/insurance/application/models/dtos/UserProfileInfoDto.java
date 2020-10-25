package com.insurance.application.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Optional;

public class UserProfileInfoDto {

    private String firstname;
    private String lastname;
    private String birthdate;
    private String email;
    private String phoneNumber;
    private String address;
    private String accidentLastYear;

    @JsonIgnore
    private int role;

    public UserProfileInfoDto() {
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public int getRole() {
        return role;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getAccidentLastYear() {
        return accidentLastYear;
    }

    public void setAccidentLastYear(String accidentLastYear) {
        this.accidentLastYear = accidentLastYear;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRole(int role) {
        this.role = role;
    }
}


