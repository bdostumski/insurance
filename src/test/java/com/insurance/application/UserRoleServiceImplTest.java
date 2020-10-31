package com.insurance.application;

import com.insurance.application.models.UserRole;
import com.insurance.application.repositories.Impl.UserRolesRepositoryImpl;
import com.insurance.application.repositories.UserRolesRepository;
import com.insurance.application.services.Impl.UserRoleServiceImpl;
import com.insurance.application.services.UserRolesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceImplTest {

    UserRolesRepository mokRepository = Mockito.mock(UserRolesRepositoryImpl.class);
    UserRolesService mokModelService = new UserRoleServiceImpl(mokRepository);

    @Test
    public void should_Create_UserRole() {
        // Arrange
        UserRole role = new UserRole();
        role.setId(1);
        role.setAuthority("ROLE_ADMIN");
        Mockito.when(mokRepository.getByValue("ROLE_ADMIN")).thenReturn(role);

        //Act
        mokModelService.create(role);

        // Assert
        Mockito.verify(mokRepository,
                Mockito.times(1)).create(role);
    }

    @Test
    public void getByValue_Should_Return_Role() {
        UserRole role = new UserRole();
        role.setId(1);
        role.setAuthority("ROLE_ADMIN");
        Mockito.when(mokRepository.getByValue("ROLE_ADMIN")).thenReturn(role);

        // Act
        UserRole result = mokModelService.getByValue("ROLE_ADMIN");

        // Assert
        Assertions.assertEquals("ROLE_ADMIN", result.getAuthority());
    }
}
