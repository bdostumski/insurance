package com.insurance.application;

import com.insurance.application.repositories.BaseAmountRepository;
import com.insurance.application.repositories.Impl.BaseAmountRepositoryImpl;
import com.insurance.application.services.BaseAmountService;
import com.insurance.application.services.Impl.BaseAmountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BaseAmountServiceImplTests {

    BaseAmountRepository mokRepository = Mockito.mock(BaseAmountRepositoryImpl.class);
    BaseAmountService mokCarService = new BaseAmountServiceImpl(mokRepository);

    @Test
    public void baseAmountShouldBeCalculated(){

        Mockito.when(mokCarService.getBaseAmount(1000, 3)).thenReturn(403.25);
        Assertions.assertEquals(403.25, mokCarService.getBaseAmount(1000, 3));

    }
}
