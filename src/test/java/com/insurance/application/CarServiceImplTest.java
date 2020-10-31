package com.insurance.application;

import com.insurance.application.exceptions.exceptionclasses.DuplicateEntityException;
import com.insurance.application.models.Car;
import com.insurance.application.repositories.CarRepository;
import com.insurance.application.repositories.Impl.CarRepositoryImpl;
import com.insurance.application.services.CarService;
import com.insurance.application.services.Impl.CarServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CarServiceImplTest {

    CarRepository mokRepository = Mockito.mock(CarRepositoryImpl.class);
    CarService mokCarService = new CarServiceImpl(mokRepository);

    @Test
    public void getById_Should_Return_When_MatchExist() {
        // Arrange
        Car car = new Car();
        car.setId(10);
        Mockito.when(mokRepository.getById(10)).thenReturn(car);

        // Act
        Car result = mokCarService.getById(10);

        // Assert
        Assertions.assertEquals(10, result.getId());
    }

    @Test
    public void car_Should_Throws_DuplicatedEntityException() {
        // Arrange
        Car car = new Car();
        car.setId(1);
        Mockito.when(mokRepository.getById(1)).thenReturn(car);

        // Act Assert
        Assertions.assertThrows(DuplicateEntityException.class,
                () -> mokCarService.create(car));
    }

    @Test
    public void create_Should_CreateCar() {
        // Arrange
        Car car = new Car();
        Mockito.when(mokRepository.getById(1)).thenReturn(car);

        // Act
        mokCarService.create(car);

        // Assert
        Mockito.verify(mokRepository,
                Mockito.times(1)).create(car);
    }

    @Test
    public void update_Should_UpdateCar() {

        // Arrange
        Car car = new Car();
        Mockito.when(mokRepository.getById(1)).thenReturn(car);

        // Act
        mokCarService.create(car);
        car.setId(10);
        mokCarService.update(car);

        // Assert
        Mockito.verify(mokRepository,
                Mockito.times(1)).update(car);
    }

    @Test
    public void delete_Should_DeleteCar() {

        // Arrange
        Car car = new Car();
        Mockito.when(mokRepository.getById(1)).thenReturn(car);

        // Act
        mokCarService.create(car);
        mokCarService.delete(car);

        // Assert
        Mockito.verify(mokRepository,
                Mockito.times(1)).delete(car);
    }

}
