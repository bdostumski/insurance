package com.insurance.application.repositories;

import com.insurance.application.models.EmailVerificationToken;
import com.insurance.application.models.UserInfo;

public interface EmailVerificationTokenRepository {

    void create(EmailVerificationToken token);

    EmailVerificationToken findByToken(String token);

    void delete(String token);
}
