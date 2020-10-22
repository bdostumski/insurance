package com.insurance.application.security.jwt;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final int userId;

    public JwtResponse(int userId) {
        this.userId = userId;
    }

    public int getToken() {
        return this.userId;
    }
}