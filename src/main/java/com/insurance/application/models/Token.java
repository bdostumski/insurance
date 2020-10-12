package com.insurance.application.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "email_tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "token")
    private String tokenValue;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserInfo user;

    public Token() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token1 = (Token) o;
        return id == token1.id &&
                tokenValue.equals(token1.tokenValue) &&
                expiryDate.equals(token1.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tokenValue, expiryDate);
    }
}
