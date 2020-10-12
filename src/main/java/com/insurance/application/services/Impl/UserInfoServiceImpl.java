package com.insurance.application.services.Impl;

import com.insurance.application.exceptions.DuplicateEntityException;
import com.insurance.application.exceptions.EntityNotFoundException;
import com.insurance.application.models.UserInfo;
import com.insurance.application.repositories.UserInfoRepository;
import com.insurance.application.services.UserInfoService;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private static final boolean ACCOUNNT_STATUS_NOT_ACTIVE = false;

    UserInfoRepository repository;

    public UserInfoServiceImpl(UserInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(UserInfo user) {

        repository.create(user);
    }

    @Override
    public void update(UserInfo user) {
        repository.update(user);
    }

    @Override
    public void delete(int id) {
        UserInfo user = getById(id);

        user.setEnabled(ACCOUNNT_STATUS_NOT_ACTIVE);
        repository.delete(user);
    }

    @Override
    public UserInfo getById(int id) {
        UserInfo user = repository.getById(id);

        return user;
    }

    @Override
    public UserInfo getByLastName(String userName) {
        UserInfo user = repository.getByLastName(userName);

        return user;
    }

    @Override
    public UserInfo getByEmail(String userEmail) {
        UserInfo user = repository.getByEmail(userEmail);

        return user;
    }
}
