package com.insurance.application;

import com.insurance.application.exceptions.exceptionclasses.EntityNotFoundException;
import com.insurance.application.models.Token;
import com.insurance.application.repositories.VerificationTokenRepository;
import com.insurance.application.services.Impl.VerificationTokenServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class VerificatonTokenServiceImplTests {


    @Mock
    VerificationTokenRepository mockTokenRepository;

    @InjectMocks
    VerificationTokenServiceImpl tokenService;

    @Test
    public void findByTokenValueShouldReturnTokenWhenPresent(){

        Token token = new Token();
        token.setTokenValue("token");
        token.setId(1);

        Mockito.when(mockTokenRepository.findByToken("token")).thenReturn(token);

        Token result = tokenService.findByToken("token");

        Assertions.assertEquals(1, result.getId());
        Assertions.assertEquals("token", result.getTokenValue());

    }

}
