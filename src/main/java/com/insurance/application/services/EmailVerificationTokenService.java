package com.insurance.application.services;

import com.insurance.application.models.EmailVerificationToken;
import com.insurance.application.models.UserInfo;

public interface EmailVerificationTokenService {

    EmailVerificationToken findByToken(String token);

    void saveToken(String token, UserInfo user);

    void delete(String token);
}
