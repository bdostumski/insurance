package com.insurance.application.services.Impl;

import com.insurance.application.models.CarModel;
import com.insurance.application.repositories.CarModelRepository;
import com.insurance.application.services.CarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarModelServiceImpl implements CarModelService {

    private final CarModelRepository repository;

    @Autowired
    public CarModelServiceImpl(CarModelRepository repository) {
        this.repository = repository;
    }

    @Override
    public CarModel getById(int id) {
        return repository.getById(id);
    }

    @Override
    public List<CarModel> getAll() {
        return repository.getAll();
    }

    @Override
    public CarModel getByModelName(String modelName) {
        return repository.getByModelName(modelName);
    }

}
