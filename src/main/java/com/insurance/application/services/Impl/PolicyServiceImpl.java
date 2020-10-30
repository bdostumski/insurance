package com.insurance.application.services.Impl;

import com.insurance.application.exceptions.exceptionclasses.EntityNotFoundException;
import com.insurance.application.models.Policy;
import com.insurance.application.repositories.PolicyRepository;
import com.insurance.application.services.PolicyService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepository repository;

    public PolicyServiceImpl(PolicyRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Policy policy) {
        repository.create(policy);
    }

    @Override
    public void update(Policy policy) {
        repository.update(policy);
    }

    @Override
    public void delete(Policy policy) {
        repository.delete(policy);
    }

    @Override
    public Policy getById(int id) {
        Policy policy = repository.getById(id);
        if (policy == null ) {
            throw new EntityNotFoundException(
                    String.format("Policy with id %d not found!", id));
        }
        return policy;
    }

    @Override
    public List<Policy> getAllPolicies() {
        return repository.getAllPolicies();
    }

    @Override
    public List<Policy> getByUserMail(String userMail) {
       List<Policy> policyList = repository.getByUserMail(userMail);
        if (policyList == null ) {
            policyList = new ArrayList<>();
//            throw new EntityNotFoundException(  TODO -> empty or throw?
//                    String.format("No policies found"));
        }
        return policyList;
    }

    @Override
    public List<Policy> getByUserId(int userId) {
        List<Policy> policyList = repository.getByUserId(userId);
        if (policyList == null ) {
            policyList = new ArrayList<>();
//            throw new EntityNotFoundException(  TODO -> empty or throw?
//                    String.format("No policies found"));
        }
        return policyList;
    }
}
