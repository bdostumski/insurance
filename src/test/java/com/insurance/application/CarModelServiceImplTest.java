package com.insurance.application;

import com.insurance.application.models.CarModel;
import com.insurance.application.repositories.CarModelRepository;
import com.insurance.application.repositories.Impl.CarModelRepositoryImpl;
import com.insurance.application.services.CarModelService;
import com.insurance.application.services.Impl.CarModelServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CarModelServiceImplTest {

    CarModelRepository mokRepository = Mockito.mock(CarModelRepositoryImpl.class);
    CarModelService mokModelService = new CarModelServiceImpl(mokRepository);

    @Test
    public void getById_Should_Return_When_MatchExist() {
        // Arrange
        CarModel model = new CarModel();
        model.setId(10);
        model.setModel("XC90");
        Mockito.when(mokRepository.getById(10)).thenReturn(model);

        // Act
        CarModel carModel = mokModelService.getById(10);

        // Assert
        Assertions.assertEquals(10, carModel.getId());
    }

    @Test
    public void getByModelName_Should_Return_CreateModel() {
        // Arrange
        CarModel model = new CarModel();
        model.setId(1);
        model.setModel("XC90");
        Mockito.when(mokRepository.getByModelName("XC90")).thenReturn(model);

        // Act
        CarModel carModel = mokModelService.getByModelName("XC90");

        // Assert
        Assertions.assertEquals("XC90", carModel.getModel());
    }
}
