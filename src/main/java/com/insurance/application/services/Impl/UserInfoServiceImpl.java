package com.insurance.application.services.Impl;

import com.insurance.application.exceptions.exceptionclasses.EmailExistsExeption;
import com.insurance.application.exceptions.exceptionclasses.EntityNotFoundException;
import com.insurance.application.models.UserInfo;
import com.insurance.application.repositories.UserInfoRepository;
import com.insurance.application.services.UserInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private static final boolean ACCOUNNT_STATUS_NOT_ACTIVE = false;

    UserInfoRepository repository;

    public UserInfoServiceImpl(UserInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserInfo> getAll() {
        return repository.getAll();
    }

    @Override
    public void create(UserInfo user) {
        try {
            repository.create(user);
        } catch (Exception e) {
            throw new EmailExistsExeption("Email already exists", e.getCause());
        }
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

    @Override
    public boolean emailAlreadyExists(String userEmail) {
        try {
          return getByEmail(userEmail).isEnabled();
        }
        catch (EntityNotFoundException ex){
            return false;
        }
    }
}
