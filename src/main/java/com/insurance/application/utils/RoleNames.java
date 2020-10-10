package com.insurance.application.utils;

public enum RoleNames {


    ROLE_USER,
    ROLE_ADMIN,
    ROLE_AGENT;


    @Override
    public String toString() {
        switch(this) {
            case ROLE_USER:
                return "ROLE_USER";
            case ROLE_ADMIN:
                return "ROLE_ADMIN";
            case ROLE_AGENT:
                return "ROLE_AGENT";
            default:
                throw new IllegalArgumentException("Role Not FOUND!");
        }
    }


}
