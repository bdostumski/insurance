package com.insurance.application.security;

import com.insurance.application.events.OnCreateAccountEvent;
import com.insurance.application.models.Token;
import com.insurance.application.models.UserInfo;
import com.insurance.application.models.UserRole;
import com.insurance.application.models.dtos.AccountRegDto;
import com.insurance.application.services.UserInfoService;
import com.insurance.application.services.UserRolesService;
import com.insurance.application.services.VerificationTokenService;
import com.insurance.application.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.insurance.application.utils.Constants.AUTHORITY_USER;

@Component
public class UserRegistrationHandler {

    UserInfoService userInfoService;
    VerificationTokenService tokenService;
    ApplicationEventPublisher eventPublisher;
    UserRolesService rolesService;
    PasswordEncoder encoder;

    @Autowired
    public UserRegistrationHandler(UserInfoService userInfoService, VerificationTokenService tokenService,
                                   ApplicationEventPublisher eventPublisher, UserRolesService rolesService,
                                   PasswordEncoder encoder) {
        this.userInfoService = userInfoService;
        this.tokenService = tokenService;
        this.eventPublisher = eventPublisher;
        this.rolesService = rolesService;
        this.encoder = encoder;
    }


    public void startUserRegistration(AccountRegDto accountDto, String token, HttpServletRequest request) {

        UserInfo user = new UserInfo();
        user.setEnabled(false);
        UserRole role = rolesService.getByValue(AUTHORITY_USER);
        user.setUserRole(role);
        user.setPassword(encoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        userInfoService.create(user);

        tokenService.saveToken(token, user);

        final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + ":" + request.getContextPath();

        sendVerificationEmail(user, token, appUrl);

    }

    public void finishUserRegistration(String token){
        Token verificationToken = tokenService.findByToken(token);
        UserInfo user = verificationToken.getUser();
        user.setEnabled(true);
        userInfoService.update(user);
    }


    private void sendVerificationEmail(UserInfo user, String token, String appURL) {
        eventPublisher.publishEvent(new OnCreateAccountEvent(appURL, user, token));
    }

}
