package com.insurance.application.models.dtos;

import javax.validation.constraints.Email;

public class AccountRegDto {

    @Email(message = "Enter a valid email address.")
    private String email;
    private String password;

    public AccountRegDto() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
