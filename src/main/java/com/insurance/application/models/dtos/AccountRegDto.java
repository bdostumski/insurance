package com.insurance.application.models.dtos;

public class AccountRegDto {

    private String email;
    private String password;
    private String receivedTokenValue;

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

    public String getReceivedTokenValue() {
        return receivedTokenValue;
    }

    public void setReceivedTokenValue(String receivedTokenValue) {
        this.receivedTokenValue = receivedTokenValue;
    }
}
