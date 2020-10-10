package com.insurance.application.services;

import com.insurance.application.models.Car;
import com.insurance.application.models.Policy;

public interface CarService {

    void create(Car car);

    void update(Car car);

    void delete(Car car);

    Car getById(int id);
}
