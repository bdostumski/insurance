package com.insurance.application.services;

import com.insurance.application.models.CarModel;

import java.util.List;

public interface CarModelService {

    CarModel getById(int id);

    List<CarModel> getAll();

    CarModel getByModelName(String modelName);

}
