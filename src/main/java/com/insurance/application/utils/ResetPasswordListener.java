package com.insurance.application.utils;

import com.insurance.application.models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class ResetPasswordListener implements ApplicationListener<OnResetPasswordEvent> {

    @Autowired
    private MailSender mailSender;

//    @Qualifier("messageSource")
//    @Autowired
//    MessageSource messages;

    @Override
    public void onApplicationEvent(OnResetPasswordEvent event) {
        this.resetPassword(event);
    }

    private void resetPassword(OnResetPasswordEvent event) {
        UserInfo user = event.getUser();

        String recipient = user.getEmail();
        String subject = "Password Change";
        String url= event.getAppUrl() + "/passwordresetter?token=" + event.getToken();
        String message = "This is an automatic email to change your password upon request. \n " +
                "If you haven't requested a password change, please ignore the provided link and contact you administrator" ;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipient);
        email.setSubject(subject);
        email.setText(message + "\n" + url);
        mailSender.send(email);

    }
}
