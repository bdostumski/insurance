package com.insurance.application.controllers.rest;

import com.insurance.application.models.Car;
import com.insurance.application.models.CarBrand;
import com.insurance.application.models.CarModel;
import com.insurance.application.services.CarBrandService;
import com.insurance.application.services.CarModelService;
import com.insurance.application.services.CarService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v.1.0/api/car")
public class CarController {

    private final CarService carService;
    private final CarModelService carModelService;
    private final CarBrandService carBrandService;


    @Autowired
    public CarController(CarService carService, CarModelService carModelService, CarBrandService carBrandService) {
        this.carService = carService;
        this.carModelService = carModelService;
        this.carBrandService = carBrandService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get car by ID", response = Car.class)
    public Car getById(@PathVariable int id) {
        return carService.getById(id);
    }

    @GetMapping("/models")
    @ApiOperation(value = "Get car models", response = List.class)
    public List<CarModel> getCarModels() {
        return carModelService.getAll();
    }

    @GetMapping("/brands")
    @ApiOperation(value = "Get car brands", response = List.class)
    public List<CarBrand> getCarBrands() {
        return carBrandService.getAll();
    }

    @GetMapping("/models/brand/{brandId}")
    @ApiOperation(value = "Get car models from brand ID", response = List.class)
    public List<CarModel> getModelsFromBrandId(@PathVariable("brandId") int brandId) {
        CarBrand brand = carBrandService.getById(brandId);
        return new ArrayList<>(brand.getCarModelSet());
    }

}
