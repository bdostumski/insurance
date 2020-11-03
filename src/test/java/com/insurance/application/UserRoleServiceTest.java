package com.insurance.application;

import com.insurance.application.models.UserRole;
import com.insurance.application.repositories.UserRolesRepository;
import com.insurance.application.services.Impl.UserRoleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceTest {

    @Mock
    UserRolesRepository mockRolesRepo;

    @InjectMocks
    UserRoleServiceImpl roleService;

    @Test
    public void findBuRoleNameShouldReturnUserRole(){

        final UserRole role = new UserRole();
        role.setId(1);
        role.setAuthority("ROLE_USER");

        Mockito.when(mockRolesRepo.getByValue("ROLE_USER")).thenReturn(role);

        UserRole result = roleService.getByValue("ROLE_USER");

        Assertions.assertEquals(1, result.getId());
        Assertions.assertEquals("ROLE_USER", result.getAuthority());

    }
}
