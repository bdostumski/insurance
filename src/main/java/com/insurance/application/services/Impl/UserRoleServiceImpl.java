package com.insurance.application.services.Impl;

import com.insurance.application.models.UserRole;
import com.insurance.application.repositories.UserRolesRepository;
import com.insurance.application.services.UserRolesService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRolesService {

    UserRolesRepository repository;

    public UserRoleServiceImpl(UserRolesRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(UserRole role) {
        repository.create(role);
    }

    @Override
    public UserRole getByValue(String value) {
        return repository.getByValue(value);
    }
}
