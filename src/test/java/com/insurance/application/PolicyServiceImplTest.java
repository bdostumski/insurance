package com.insurance.application;

import com.insurance.application.models.Policy;
import com.insurance.application.repositories.Impl.PolicyRepositoryImpl;
import com.insurance.application.repositories.PolicyRepository;
import com.insurance.application.services.Impl.PolicyServiceImpl;
import com.insurance.application.services.PolicyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PolicyServiceImplTest {

    PolicyRepository mokRepository = Mockito.mock(PolicyRepositoryImpl.class);
    PolicyService mokModelService = new PolicyServiceImpl(mokRepository);

    @Test
    public void create_Should_Policy() {
        // Arrange
        Policy policy = new Policy();
        Mockito.when(mokRepository.getById(1)).thenReturn(policy);

        //Act
        mokModelService.create(policy);

        // Assert
        Mockito.verify(mokRepository,
                Mockito.times(1)).create(policy);
    }

    @Test
    public void update_Should_Update_Policy() {
        // Arrange
        Policy policy = new Policy();
        Mockito.when(mokRepository.getById(2)).thenReturn(policy);

        //Act
        mokModelService.create(policy);
        policy.setId(2);
        mokModelService.update(policy);

        // Assert
        Mockito.verify(mokRepository,
                Mockito.times(1)).update(policy);
    }

    @Test
    public void delete_Should_Delete_InfoDto() {

        // Arrange
        Policy policy = new Policy();
        Mockito.when(mokRepository.getById(2)).thenReturn(policy);

        //Act
        mokModelService.create(policy);
        mokModelService.delete(policy);

        // Assert
        Mockito.verify(mokRepository,
                Mockito.times(1)).delete(policy);
    }

    @Test
    public void getById_Should_Return_InfoDto() {
        // Arrange
        Policy policy = new Policy();
        policy.setId(1);

        Mockito.when(mokRepository.getById(1)).thenReturn(policy);

        // Act
        Policy result = mokModelService.getById(1);

        // Assert
        Assertions.assertEquals(1, result.getId());
    }
}
