package com.insurance.application.repositories;

import com.insurance.application.models.Car;
import com.insurance.application.models.Policy;

public interface CarRepository {

    void create(Car car);

    void update(Car car);

    void delete(Car car);

    Car getById(int id);
}


