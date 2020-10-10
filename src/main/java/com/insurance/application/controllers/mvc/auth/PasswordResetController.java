package com.insurance.application.controllers.mvc.auth;


import com.insurance.application.exceptions.EmailExistsExeption;
import com.insurance.application.models.Token;
import com.insurance.application.models.UserInfo;
import com.insurance.application.services.UserInfoService;
import com.insurance.application.services.VerificationTokenService;
import com.insurance.application.utils.OnCreateAccountEvent;
import com.insurance.application.utils.OnResetPasswordEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.UUID;

@Controller
public class PasswordResetController {

    UserInfoService userInfoService;
    VerificationTokenService tokenService;
    ApplicationEventPublisher eventPublisher;

    public PasswordResetController(UserInfoService userInfoService, VerificationTokenService tokenService, ApplicationEventPublisher eventPublisher) {
        this.userInfoService = userInfoService;
        this.tokenService = tokenService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/recoverpassword")
    public String getLogin() {
        return "resetpassword";
    }

    @RequestMapping("/passwordreset/user")
    public String sendMail(@Valid @RequestParam(name = "email") final String userEmail,
//                           final BindingResult result,
                           final HttpServletRequest request,
                           RedirectAttributes redirectAttributes) {
//        if (result.hasErrors()) {
//            return "register";
//        }
        try {

            UserInfo user = userInfoService.getByEmail(userEmail);

            final String token = UUID.randomUUID().toString();
            tokenService.saveToken(token, user);

            final String appURL = "http://" + request.getServerName() + ":" + request.getServerPort() + ":" + request.getContextPath();
            sendPasswordChangedMail(user, token, appURL);
        } catch (EmailExistsExeption e) {
//            result.addError(new FieldError("user", "email", e.getMessage()));
            return "register";
        }
        redirectAttributes.addFlashAttribute("message", "If this e-mail exists, we've sent a new password");
        return "redirect:/index";
    }

    private void sendPasswordChangedMail(UserInfo user, String token, String appURL) {
        eventPublisher.publishEvent(new OnResetPasswordEvent(appURL, user, token));
    }

    @GetMapping(value = "/passwordreset/user")
    public ModelAndView openPasswordChanger(
            @RequestParam("token") String token) {
        try {

            Token verificationToken = tokenService.findByToken(token);
            UserInfo user = verificationToken.getUser();

            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority(user.getUserRole().getAuthority())));
            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView("passress");
    }

    @PostMapping(value = "/passress")
    public String saveNewPassword(
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmedPassword,
            RedirectAttributes redirectAttributes) {
        try {
            if (password.equals(confirmedPassword)) {

                UserInfo user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                user.setPassword(password);
                userInfoService.update(user);
                redirectAttributes.addFlashAttribute("message", "Password changed successfully");
//        tokenService.delete(token); TODO
            } else {
                return "passress";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/login";
    }


}