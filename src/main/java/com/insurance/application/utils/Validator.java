package com.insurance.application.utils;

import com.insurance.application.models.UserInfo;
import com.insurance.application.services.UserInfoService;

import java.security.Principal;

public class Validator {

    public static UserInfo loadUser(Principal principal, UserInfoService userInfoService) {
        if (principal != null) {
            return userInfoService.getByEmail(principal.getName());
        } else {
            return new UserInfo();
        }
    }
}
