package com.insurance.application.services.Impl;

import com.insurance.application.models.EmailVerificationToken;
import com.insurance.application.models.UserInfo;
import com.insurance.application.repositories.EmailVerificationTokenRepository;
import com.insurance.application.services.EmailVerificationTokenService;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationTokenServiceImpl implements EmailVerificationTokenService {
    EmailVerificationTokenRepository repository;

    public EmailVerificationTokenServiceImpl(EmailVerificationTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public EmailVerificationToken findByToken(String token) {
        return repository.findByToken(token);
    }

    @Override
    public void saveToken(String token, UserInfo user) {
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        emailVerificationToken.setToken(token);
        emailVerificationToken.setUser(user);
        repository.create(emailVerificationToken);
    }

    @Override
    public void delete(String token) {
        repository.delete(token);
    }
}
