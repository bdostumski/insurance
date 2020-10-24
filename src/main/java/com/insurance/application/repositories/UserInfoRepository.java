package com.insurance.application.repositories;

import com.insurance.application.models.UserInfo;

import java.util.List;

public interface UserInfoRepository {

    List<UserInfo> getAll();

    void create(UserInfo user);

    void update(UserInfo user);

    void delete(UserInfo user);

    UserInfo getById(int id);

    UserInfo getByLastName(String userName);

    UserInfo getByEmail(String userEmail);
}
