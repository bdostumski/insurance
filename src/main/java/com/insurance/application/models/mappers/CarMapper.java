package com.insurance.application.models.mappers;

import com.insurance.application.models.Car;
import com.insurance.application.models.CarModel;
import com.insurance.application.models.UserInfo;
import com.insurance.application.models.dtos.InitialInfoDto;
import com.insurance.application.models.dtos.InitialInfoStringDto;
import com.insurance.application.services.CarModelService;
import com.insurance.application.services.CarService;

public class CarMapper {

    public static Car enlistCar(InitialInfoDto infoDto,
                                CarModelService carModelService,
                                CarService carService,
                                UserInfo user) {

        Car car = new Car();
        CarModel model = carModelService.getById(infoDto.getCarModel());
        car.setUserInfo(user);
        car.setCarModel(model);
        car.setRegDate(infoDto.getRegistrationDate());
        car.setCubicCap(infoDto.getCarCubic());
        carService.create(car);

        return car;
    }

    public static Car enlistCar(InitialInfoStringDto infoDto,
                                CarModelService carModelService,
                                CarService carService,
                                UserInfo user) {

        Car car = new Car();
        CarModel model = carModelService.getByModelName(infoDto.getCarModel());
        car.setUserInfo(user);
        car.setCarModel(model);
        car.setRegDate(infoDto.getRegistrationDate());
        car.setCubicCap(infoDto.getCarCubic());
        carService.create(car);

        return car;
    }
}
