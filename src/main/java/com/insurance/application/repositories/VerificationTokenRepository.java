package com.insurance.application.repositories;

import com.insurance.application.models.Token;

public interface VerificationTokenRepository {

    void create(Token token);

    Token findByToken(String token);

    void delete(Token token);
}
