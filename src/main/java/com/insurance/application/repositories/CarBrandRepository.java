package com.insurance.application.repositories;

import com.insurance.application.models.CarBrand;

import java.util.List;

public interface CarBrandRepository {

    CarBrand getById(int id);

    List<CarBrand> getAll();

    CarBrand getByBrandName(String brandName);

}
