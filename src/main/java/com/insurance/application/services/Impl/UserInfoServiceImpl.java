package com.insurance.application.services.Impl;

import com.insurance.application.models.UserInfo;
import com.insurance.application.repositories.UserInfoRepository;
import com.insurance.application.services.UserInfoService;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

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
        repository.delete(id);
    }

    @Override
    public UserInfo getById(int id) {
        return repository.getById(id);
    }

    @Override
    public UserInfo getByLastName(String userName) {
        return repository.getByLastName(userName);
    }

    @Override
    public UserInfo getByEmail(String userEmail) {
        return repository.getByEmail(userEmail);
    }
}
