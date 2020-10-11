package com.insurance.application.services.Impl;

import com.insurance.application.models.Coefficient;
import com.insurance.application.repositories.CoefficientRepository;
import com.insurance.application.services.CoefficientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoefficientServiceImpl implements CoefficientService {

    CoefficientRepository repository;

    @Autowired
    public CoefficientServiceImpl(CoefficientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Coefficient getById(int id) {
        return repository.getById(id);
    }
}
