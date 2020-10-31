package com.insurance.application;

import com.insurance.application.models.Coefficient;
import com.insurance.application.repositories.CoefficientRepository;
import com.insurance.application.repositories.Impl.CoefficientRepositoryImpl;
import com.insurance.application.services.CoefficientService;
import com.insurance.application.services.Impl.CoefficientServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CoefficientServiceImplTest {


    CoefficientRepository mokRepository = Mockito.mock(CoefficientRepositoryImpl.class);
    CoefficientService mokCoefficeintService = new CoefficientServiceImpl(mokRepository);

    @Test
    public void getById_Should_Return_BG_Coefficients() {
        // Arrange
        Coefficient coefficient = new Coefficient();
        coefficient.setId(1);
        coefficient.setName("BG");
        coefficient.setAccident(0.2);
        coefficient.setAgeLimit(25);
        coefficient.setAgeCoefficient(0.05);
        coefficient.setTaxAmount(0.1);

        Mockito.when(mokRepository.getById(1)).thenReturn(coefficient);

        // Act
        Coefficient result = mokCoefficeintService.getById(1);

        // Assert
        Assertions.assertEquals("BG", result.getName());
    }

}
