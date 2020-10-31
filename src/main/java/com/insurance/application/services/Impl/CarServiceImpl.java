package com.insurance.application.services.Impl;

import com.insurance.application.exceptions.exceptionclasses.DuplicateEntityException;
import com.insurance.application.models.Car;
import com.insurance.application.repositories.CarRepository;
import com.insurance.application.services.CarService;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {

    CarRepository repository;

    public CarServiceImpl(CarRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Car car) {
        if (repository.getById(car.getId()) != null) {
            throw new DuplicateEntityException("This car already exists");
        }
        repository.create(car);
    }

    @Override
    public void update(Car car) {
        repository.update(car);
    }

    @Override
    public void delete(Car car) {
        repository.delete(car);
    }

    @Override
    public Car getById(int id) {
        return repository.getById(id);
    }
}
