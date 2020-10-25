package com.insurance.application.repositories;

import com.insurance.application.models.Policy;

import java.util.List;

public interface PolicyRepository {

    void create(Policy policy);

    void update(Policy policy);

    void delete(Policy policy);

    Policy getById(int id);

    List<Policy> getByUserMail(String userMail);

    List<Policy> getAllPolicies();

    List<Policy> getByUserId(int userId);
}
