package com.insurance.application.services.Impl;

import com.insurance.application.repositories.BaseAmountRepository;
import com.insurance.application.services.BaseAmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseAmountServiceImpl implements BaseAmountService {

    BaseAmountRepository repository;

    @Autowired
    public BaseAmountServiceImpl(BaseAmountRepository repository) {
        this.repository = repository;
    }

    @Override
    public double getBaseAmount(int car_cubic, int car_age) {
        return repository.getBaseAmount(car_cubic, car_age);
    }

    @Override
    public List<Integer> getMaxCarCubics() {
        return repository.getMaxCarCubics();
    }

}
