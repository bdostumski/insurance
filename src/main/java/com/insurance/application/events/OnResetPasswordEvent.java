package com.insurance.application.events;

import com.insurance.application.models.UserInfo;
import org.springframework.context.ApplicationEvent;

public class OnResetPasswordEvent extends ApplicationEvent {

    private String appUrl;
    private UserInfo user;
    private String token;

    public OnResetPasswordEvent(String appUrl, UserInfo user, String token) {
        super(user);

        this.appUrl = appUrl;
        this.user = user;
        this.token = token;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public UserInfo getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
