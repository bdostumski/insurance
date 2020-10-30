package com.insurance.application.services;

import com.insurance.application.models.Token;
import com.insurance.application.models.UserInfo;

public interface VerificationTokenService {

    Token findByToken(String token);

    void saveToken(String token, UserInfo user);

    void delete(Token token);
}
