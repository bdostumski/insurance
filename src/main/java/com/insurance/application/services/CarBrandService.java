package com.insurance.application.services;

import com.insurance.application.models.CarBrand;

import java.util.List;

public interface CarBrandService {

    CarBrand getById(int id);

    List<CarBrand> getAll();

    CarBrand getByBrandName(String brandName);
}
