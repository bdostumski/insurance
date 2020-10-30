package com.insurance.application.services.Impl;

import com.insurance.application.models.Token;
import com.insurance.application.models.UserInfo;
import com.insurance.application.repositories.VerificationTokenRepository;
import com.insurance.application.services.VerificationTokenService;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
    VerificationTokenRepository repository;

    public VerificationTokenServiceImpl(VerificationTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public Token findByToken(String token) {
        return repository.findByToken(token);
    }

    @Override
    public void saveToken(String token, UserInfo user) {
        Token emailVerificationToken = new Token();
        emailVerificationToken.setTokenValue(token);
        emailVerificationToken.setUser(user);
        repository.create(emailVerificationToken);
    }

    @Override
    public void delete(Token token) {
        repository.delete(token);
    }
}
