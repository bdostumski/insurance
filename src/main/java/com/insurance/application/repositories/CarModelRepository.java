package com.insurance.application.repositories;

import com.insurance.application.models.CarModel;

import java.util.List;

public interface CarModelRepository {

    CarModel getById(int id);

    List<CarModel> getAll();

    CarModel getByModelName(String modelName);

}
