package com.insurance.application.utils;

import com.insurance.application.models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;


@Component
public class CreateAccountListener implements ApplicationListener<OnCreateAccountEvent> {


    @Autowired
    private MailSender mailSender;

    @Override
    public void onApplicationEvent(OnCreateAccountEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnCreateAccountEvent event) {
        UserInfo user = event.getUser();

        String recipient = user.getEmail();
        String subject = "Registration Confirmation";
        String url= event.getAppUrl() + "/registrationconfirm?token=" + event.getToken();
        String message = "Please confirm your account";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipient);
        email.setSubject(subject);
        email.setText(message + "\n" + url);
        mailSender.send(email);

    }
}