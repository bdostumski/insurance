package com.insurance.application.controllers.mvc;

import com.insurance.application.exceptions.EmailExistsExeption;
import com.insurance.application.models.EmailVerificationToken;
import com.insurance.application.models.UserInfo;
import com.insurance.application.services.EmailVerificationTokenService;
import com.insurance.application.services.UserInfoService;
import com.insurance.application.utils.OnCreateAccountEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.Binding;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@Controller
public class RegisterController {

    UserInfoService userInfoService;
    EmailVerificationTokenService tokenService;
    ApplicationEventPublisher eventPublisher;

    public RegisterController(UserInfoService userInfoService, EmailVerificationTokenService tokenService, ApplicationEventPublisher eventPublisher) {
        this.userInfoService = userInfoService;
        this.tokenService = tokenService;
        this.eventPublisher = eventPublisher;
    }

    @RequestMapping("/sign-up")
    public ModelAndView registrationForm() {
        return new ModelAndView("register", "user", new UserInfo());
    }

    @RequestMapping("/register/user")
    public ModelAndView registerUser(@Valid final UserInfo user, final BindingResult result, final HttpServletRequest request) {
        if (result.hasErrors()) {
            return new ModelAndView("register", "user", user);
        }
        try {
            user.setEnabled(false);
            userInfoService.create(user);

            final String token = UUID.randomUUID().toString();
            tokenService.saveToken(token, user);

            final String appURL = "http://" + request.getServerName() + ":" + request.getServerPort() + ":" + request.getContextPath();
            sendVerificationEmail(user, token, appURL);
        } catch (EmailExistsExeption e) {
            result.addError(new FieldError("user", "email", e.getMessage()));
            return new ModelAndView("register", "user", user);
        }
        return new ModelAndView("redirect:/policy");
    }


    private void sendVerificationEmail(UserInfo user, String token, String appURL) {
        eventPublisher.publishEvent(new OnCreateAccountEvent(appURL, user, token));
    }


    @RequestMapping(value = "/registrationconfirm")
    public ModelAndView confirmRegistration(
            @RequestParam("token") String token,
            RedirectAttributes redirectAttributes) {
        EmailVerificationToken verificationToken = tokenService.findByToken(token);
        UserInfo user = verificationToken.getUser();
        user.setEnabled(true);
        userInfoService.update(user);
        redirectAttributes.addFlashAttribute("message", "Your account verified successfully");
//        tokenService.delete(token); TODO

        return new ModelAndView("redirect:/login");
    }


}
