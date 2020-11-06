package com.insurance.application.controllers.mvc.auth;


import com.insurance.application.models.dtos.AccountRegDto;
import com.insurance.application.security.UserRegistrationHandler;
import com.insurance.application.services.UserInfoService;
import com.insurance.application.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.UUID;

@Controller
public class RegisterNewUserController {

    UserInfoService userInfoService;
    UserRegistrationHandler registrationHandler;

    @Autowired
    public RegisterNewUserController(UserInfoService userInfoService, UserRegistrationHandler registrationHandler) {
        this.userInfoService = userInfoService;
        this.registrationHandler = registrationHandler;
    }

    @RequestMapping("/sign-up")
    public ModelAndView registrationForm() {
        return new ModelAndView("register", "accountDto", new AccountRegDto());
    }

    @RequestMapping("/register/user")
    public ModelAndView registerUser(
            @ModelAttribute("accountDto") @Valid final AccountRegDto accountDto,
            final BindingResult result,
            HttpSession session,
            final HttpServletRequest request) {

        if (!userInfoService.emailAlreadyExists(accountDto.getEmail())) {

            String sessionToken = (String) session.getAttribute(Constants.TOKEN);
            /*
            If a session token has been saved in the session, it will be used as a maail confirmmation token.
            Else a new one would be generated
            */
            final String token = (sessionToken != null) ? sessionToken : UUID.randomUUID().toString();

            registrationHandler.startUserRegistration(accountDto, token, request);

        } else {
            result.addError(new FieldError("accountDto", "email", "Email already exists"));
        }

        if (result.hasErrors()) {
            return new ModelAndView("register", "accountDto", accountDto);
        }
        return new ModelAndView("redirect:/successful-registratoin");
    }

    @GetMapping("/successful-registratoin")
    public String getSuccessfulRegistratoin() {
        return "successful-registratoin";
    }

    @RequestMapping(value = "/registrationconfirm")
    public ModelAndView confirmRegistration(
            @RequestParam("token") String token,
            RedirectAttributes redirectAttributes) {
        try {
            registrationHandler.finishUserRegistration(token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("message", "Your account verified successfully");

        return new ModelAndView("redirect:/login");
    }
}
