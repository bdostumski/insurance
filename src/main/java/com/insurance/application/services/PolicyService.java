package com.insurance.application.services;

import com.insurance.application.models.Policy;

import java.util.List;

public interface PolicyService {

    void create(Policy policy);

    void update(Policy policy);

    void delete(Policy policy);

    Policy getById(int id);

    List<Policy> getAllPolicies();

    List<Policy> getByUserMail(String userMail);

    List<Policy> getByUserId(int userId);

}
