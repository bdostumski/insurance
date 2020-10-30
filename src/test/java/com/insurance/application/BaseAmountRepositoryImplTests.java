package com.insurance.application;

import com.insurance.application.models.BaseAmount;
import com.insurance.application.models.mappers.InitialStringMapper;
import com.insurance.application.repositories.Impl.BaseAmountRepositoryImpl;
import com.insurance.application.repositories.Impl.CoefficientRepositoryImpl;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
public class BaseAmountRepositoryImplTests {

    @InjectMocks
    InitialStringMapper mapper;

    @Test
    public void baseAmountShouldBeCalculated(){

        double baseAmount = 100;

    }
}
