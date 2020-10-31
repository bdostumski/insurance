package com.insurance.application;

import com.insurance.application.models.CarBrand;
import com.insurance.application.repositories.CarBrandRepository;
import com.insurance.application.repositories.Impl.CarBrandRepositoryImpl;
import com.insurance.application.services.CarBrandService;
import com.insurance.application.services.Impl.CarBrandServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CarBrandServiceImplTests {

    CarBrandRepository mokRepository = Mockito.mock(CarBrandRepositoryImpl.class);
    CarBrandService mokCarService = new CarBrandServiceImpl(mokRepository);

    @Test
    public void getById_Should_Return_When_MatchExist() {
        // Arrange
        CarBrand carBrand = new CarBrand();
        carBrand.setId(10);

        Mockito.when(mokRepository.getById(10)).thenReturn(carBrand);

        // Act
        CarBrand result = mokCarService.getById(10);

        // Assert
        Assertions.assertEquals(10, result.getId());
    }

    @Test
    public void getByBrandName_Should_Return_CarBrand() {
        // Arrange
        CarBrand brand = new CarBrand();
        brand.setId(1);
        brand.setBrand("Volvo");
        Mockito.when(mokRepository.getByBrandName("Volvo")).thenReturn(brand);

        // Act
        CarBrand carModel = mokCarService.getByBrandName("Volvo");

        // Assert
        Assertions.assertEquals("Volvo", carModel.getBrand());
    }
}
