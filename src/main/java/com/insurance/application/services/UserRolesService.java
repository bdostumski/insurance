package com.insurance.application.services;

import com.insurance.application.models.UserRole;

public interface UserRolesService {

    void create(UserRole role);

    UserRole getByValue(String value);

}
