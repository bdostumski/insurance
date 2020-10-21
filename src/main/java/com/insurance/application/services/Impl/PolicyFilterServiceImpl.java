package com.insurance.application.services.Impl;

import com.insurance.application.models.Policy;
import com.insurance.application.repositories.PolicyFilterRepository;
import com.insurance.application.services.PolicyFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyFilterServiceImpl implements PolicyFilterService {

    PolicyFilterRepository repository;

    @Autowired
    public PolicyFilterServiceImpl(PolicyFilterRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Policy> filterForUser(int userId, String fromDate, String toDate) {
        return repository.filterForUser(userId, fromDate, toDate);
    }

    @Override
    public List<Policy> filterForAdmin(String fromDate, String toDate, String mail) {
        return repository.filterForAdmin(fromDate, toDate, mail);
    }

}
