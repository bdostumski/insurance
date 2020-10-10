package com.insurance.application.services;

import com.insurance.application.models.Policy;

import java.util.List;

public interface PolicyService {

    void create(Policy policy);

    void update(Policy policy);

    void delete(Policy policy);

    Policy getById(int id);

    List<Policy> getByUserMail(String userMail);

}
