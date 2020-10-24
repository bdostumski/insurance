package com.insurance.application.services.Impl;

import com.insurance.application.models.Policy;
import com.insurance.application.models.UserInfo;
import com.insurance.application.repositories.FilterRepository;
import com.insurance.application.services.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterServiceImpl implements FilterService {

    FilterRepository repository;

    @Autowired
    public FilterServiceImpl(FilterRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Policy> filterForUser(int userId, String fromDate, String toDate) {
        return repository.filterForUser(userId, fromDate, toDate);
    }

    @Override
    public List<Policy> filterForAgent(String fromDate, String toDate, String mail) {
        return repository.filterForAgent(fromDate, toDate, mail);
    }

    @Override
    public List<UserInfo> filterForAdmin(String firstname, String lastname, String mail) {
        return repository.filterForAdmin(firstname, lastname, mail);
    }

}
