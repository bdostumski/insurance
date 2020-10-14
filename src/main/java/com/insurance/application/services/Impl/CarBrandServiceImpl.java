package com.insurance.application.services.Impl;

import com.insurance.application.models.CarBrand;
import com.insurance.application.repositories.CarBrandRepository;
import com.insurance.application.services.CarBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarBrandServiceImpl implements CarBrandService {

    private final CarBrandRepository repository;

    @Autowired
    public CarBrandServiceImpl(CarBrandRepository repository) {
        this.repository = repository;
    }

    @Override
    public CarBrand getById(int id) {
        return repository.getById(id);
    }

    @Override
    public List<CarBrand> getAll() {
        return repository.getAll();
    }

    @Override
    public CarBrand getByBrandName(String brandName) {
        return repository.getByBrandName(brandName);
    }
}
