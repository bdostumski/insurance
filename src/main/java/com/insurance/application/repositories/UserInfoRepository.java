package com.insurance.application.repositories;

import com.insurance.application.models.UserInfo;

public interface UserInfoRepository {
    void create(UserInfo user);

    void update(UserInfo user);

    void delete(int id);

    UserInfo getById(int id);

    UserInfo getByLastName(String userName);

    UserInfo getByEmail(String userEmail);
}
