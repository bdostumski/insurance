package com.insurance.application;

import com.insurance.application.models.Token;
import com.insurance.application.repositories.Impl.VerificationTokenRepositoryImpl;
import com.insurance.application.repositories.VerificationTokenRepository;
import com.insurance.application.services.Impl.VerificationTokenServiceImpl;
import com.insurance.application.services.VerificationTokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class VerificatonTokenServiceImplTests {

    @Test
    public void findByTokenValueShouldReturnTokenWhenPresent(){

        VerificationTokenRepository mokRepository = Mockito.mock(VerificationTokenRepositoryImpl.class);
        VerificationTokenService tokenService = new VerificationTokenServiceImpl(mokRepository);

        Token token = new Token();
        token.setTokenValue("token");
        token.setId(1);

        Mockito.when(mokRepository.findByToken("token")).thenReturn(token);

        Token result = tokenService.findByToken("token");

        Assertions.assertEquals(1, result.getId());
        Assertions.assertEquals("token", result.getTokenValue());

    }
}
