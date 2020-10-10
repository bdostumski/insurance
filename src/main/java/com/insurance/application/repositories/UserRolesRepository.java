package com.insurance.application.repositories;

import com.insurance.application.models.UserRole;

public interface UserRolesRepository {

    void create(UserRole role);

    UserRole getByValue(String value);
}
