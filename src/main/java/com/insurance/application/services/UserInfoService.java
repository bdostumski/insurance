package com.insurance.application.services;

import com.insurance.application.models.UserInfo;

import java.util.List;

public interface UserInfoService {

    List<UserInfo> getAll();

    void create(UserInfo user);

    void update(UserInfo user);

    void delete(int id);

    UserInfo getById(int id);

    UserInfo getByLastName(String userName);

    UserInfo getByEmail(String userEmail);

    boolean emailAlreadyExists(String userEmail);

}
