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
        if (repository.getByEmail(user.getEmail()) != null){
            throw new DuplicateEntityException("This user alsready exists");
        }
        repository.create(user);
    }

    @Override
    public void update(UserInfo user) {
        repository.update(user);
    }

    @Override
    public void delete(int id) {
        UserInfo user = getById(id);
        if (user == null || user.isEnabled() == ACCOUNNT_STATUS_NOT_ACTIVE) {
            throw new EntityNotFoundException(
                    String.format("User with id %d not found!", id));
        }
        user.setEnabled(ACCOUNNT_STATUS_NOT_ACTIVE);
        repository.delete(user);
    }

    @Override
    public UserInfo getById(int id) {
        UserInfo user = repository.getById(id);
        if (user == null || user.isEnabled() == ACCOUNNT_STATUS_NOT_ACTIVE) {
            throw new EntityNotFoundException(
                    String.format("User with id %d not found!", id));
        }
        return user;
    }

    @Override
    public UserInfo getByLastName(String userName) {
        UserInfo user = repository.getByLastName(userName);
        if (user == null || user.isEnabled() == ACCOUNNT_STATUS_NOT_ACTIVE) {
            throw new EntityNotFoundException(
                    String.format("User with last name %s not found!", userName));
        }
        return user;
    }

    @Override
    public UserInfo getByEmail(String userEmail) {
        UserInfo user = repository.getByEmail(userEmail);
        if (user == null || user.isEnabled() == ACCOUNNT_STATUS_NOT_ACTIVE) {
            throw new EntityNotFoundException(
                    String.format("User with email %s not found!", userEmail));
        }
        return user;
    }
}
