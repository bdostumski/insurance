package com.insurance.application;

import com.insurance.application.models.UserInfo;
import com.insurance.application.repositories.Impl.UserInfoRepositoryImpl;
import com.insurance.application.repositories.UserInfoRepository;
import com.insurance.application.services.Impl.UserInfoServiceImpl;
import com.insurance.application.services.UserInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserInfoServiceImplTest {

    UserInfoRepository mokRepository = Mockito.mock(UserInfoRepositoryImpl.class);
    UserInfoService mokModelService = new UserInfoServiceImpl(mokRepository);

    @Test
    public void should_Create_User() {
        // Arrange
        UserInfo user = new UserInfo();
        Mockito.when(mokRepository.getById(1)).thenReturn(user);

        //Act
        mokModelService.create(user);

        // Assert
        Mockito.verify(mokRepository,
                Mockito.times(1)).create(user);
    }

    @Test
    public void update_Should_Update_Policy() {
        // Arrange
        UserInfo user = new UserInfo();
        Mockito.when(mokRepository.getById(2)).thenReturn(user);

        //Act
        mokModelService.create(user);
        user.setId(2);
        mokModelService.update(user);

        // Assert
        Mockito.verify(mokRepository,
                Mockito.times(1)).update(user);
    }

    @Test
    public void delete_Should_Delete_UserInfo() {

        // Arrange
        UserInfo user = new UserInfo();
        user.setId(1);
        user.setEnabled(true);
        Mockito.when(mokRepository.getById(1)).thenReturn(user);

        //Act
        mokModelService.create(user);
        mokModelService.delete(user.getId());

        // Assert
        Mockito.verify(mokRepository,
                Mockito.times(1)).delete(user);
    }

    @Test
    public void getById_Should_Return_UserInfo() {
        // Arrange
        UserInfo user = new UserInfo();
        user.setId(1);

        Mockito.when(mokRepository.getById(1)).thenReturn(user);

        // Act
        UserInfo result = mokModelService.getById(1);

        // Assert
        Assertions.assertEquals(1, result.getId());
    }

    @Test
    public void getLastName_Should_Return_UserInfo() {
        // Arrange
        UserInfo user = new UserInfo();
        user.setId(1);
        user.setLastname("Lastname");

        Mockito.when(mokRepository.getByLastName("Lastname")).thenReturn(user);

        // Act
        UserInfo result = mokModelService.getByLastName("Lastname");

        // Assert
        Assertions.assertEquals("Lastname", result.getLastname());
    }

    @Test
    public void getEmail_Should_Return_InfoDto() {
        // Arrange
        UserInfo user = new UserInfo();
        user.setId(1);
        user.setEmail("Email");

        Mockito.when(mokRepository.getByEmail("Email")).thenReturn(user);

        // Act
        UserInfo result = mokModelService.getByEmail("Email");

        // Assert
        Assertions.assertEquals("Email", result.getEmail());
    }
}
