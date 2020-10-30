package com.insurance.application.controllers.mvc.auth;


import com.insurance.application.models.Token;
import com.insurance.application.models.UserInfo;
import com.insurance.application.services.UserInfoService;
import com.insurance.application.services.VerificationTokenService;
import com.insurance.application.events.OnResetPasswordEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Calendar;
import java.util.UUID;

@Controller
public class PasswordResetController {

    UserInfoService userInfoService;
    VerificationTokenService tokenService;
    ApplicationEventPublisher eventPublisher;
    PasswordEncoder encoder;
    private static final Logger logger = LoggerFactory.getLogger(PasswordResetController.class);

    public PasswordResetController(UserInfoService userInfoService, VerificationTokenService tokenService,
                                   ApplicationEventPublisher eventPublisher, PasswordEncoder encoder) {
        this.userInfoService = userInfoService;
        this.tokenService = tokenService;
        this.eventPublisher = eventPublisher;
        this.encoder = encoder;
    }

    @GetMapping("/recoverpassword")
    public String getLogin(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "If this e-mail exists, we've sent a new password");
        return "resetpassword";
    }

    @RequestMapping("/passwordreset/user")
    public String sendMail(@Valid @RequestParam(name = "email") final String userEmail,
                           final HttpServletRequest request,
                           RedirectAttributes redirectAttributes) {


            if (userInfoService.emailAlreadyExists(userEmail)) {

                UserInfo user = userInfoService.getByEmail(userEmail);

                String tokenValue = generateRecoveryToken(userEmail, user);

                final String appURL = "http://" + request.getServerName() + ":" + request.getServerPort() + ":" + request.getContextPath();
                sendPasswordChangedMail(user, tokenValue, appURL);

            } else {
                logger.info(" A reset wa attempted at :" + Calendar.getInstance() + " from " + request.getRequestURI());
            }

        redirectAttributes.addFlashAttribute("message", "If this e-mail exists, we've sent a new password");
        return "redirect:/login";
    }

    private String generateRecoveryToken ( String userEmail, UserInfo user){
        Token currentToken = user.getToken();
        tokenService.delete(currentToken);

        final String tokenValue = UUID.randomUUID().toString();
        tokenService.saveToken(tokenValue, user);
        return tokenValue;
    }


    private void sendPasswordChangedMail(UserInfo user, String token, String appURL) {
        eventPublisher.publishEvent(new OnResetPasswordEvent(appURL, user, token));
    }


    @GetMapping(value = "/passwordreset/user")
    public ModelAndView openPasswordChanger(
            @RequestParam("token") String token) {

        Token verificationToken = tokenService.findByToken(token);
        UserInfo user = verificationToken.getUser();

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority(user.getUserRole().getAuthority())));
        SecurityContextHolder.getContext().setAuthentication(auth);

        return new ModelAndView("passress");
    }


    @PostMapping(value = "/passress")
    public String saveNewPassword(
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmedPassword,
            RedirectAttributes redirectAttributes) {

        if (password.equals(confirmedPassword)) {

            UserInfo user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user.setPassword(encoder.encode(password));
            userInfoService.update(user);

            redirectAttributes.addFlashAttribute("message", "Password changed successfully");
        } else {
            redirectAttributes.addFlashAttribute("message", "Passwords don't match");
            return "passress";
        }
        redirectAttributes.addFlashAttribute("message", "Password reset successful");
        return "redirect:/login";
    }
}
