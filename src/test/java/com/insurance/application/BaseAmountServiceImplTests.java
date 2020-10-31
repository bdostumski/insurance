package com.insurance.application;

import com.insurance.application.models.mappers.InitialStringMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
public class BaseAmountServiceImplTests {

    @InjectMocks
    InitialStringMapper mapper;

    @Test
    public void baseAmountShouldBeCalculated(){

        double baseAmount = 100;

    }
}
