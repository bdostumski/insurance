package com.insurance.application.controllers.mvc.auth;

import com.insurance.application.exceptions.EmailExistsExeption;
import com.insurance.application.models.Token;
import com.insurance.application.models.UserInfo;
import com.insurance.application.models.UserRole;
import com.insurance.application.models.dtos.AccountRegDto;
import com.insurance.application.models.dtos.InitialInfoDto;
import com.insurance.application.models.dtos.InitialInfoStringDto;
import com.insurance.application.services.Impl.UserRoleServiceImpl;
import com.insurance.application.services.UserRolesService;
import com.insurance.application.services.VerificationTokenService;
import com.insurance.application.services.UserInfoService;
import com.insurance.application.utils.OnCreateAccountEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.util.UUID;

import static com.insurance.application.models.mappers.InitialStringMapper.initialStringMapper;

@Controller
public class RegisterNewUserController {

    UserInfoService userInfoService;
    VerificationTokenService tokenService;
    ApplicationEventPublisher eventPublisher;
    UserRolesService rolesService;
    PasswordEncoder encoder;

    public RegisterNewUserController(UserInfoService userInfoService, VerificationTokenService tokenService,
                                     ApplicationEventPublisher eventPublisher, UserRolesService rolesService, PasswordEncoder encoder) {
        this.userInfoService = userInfoService;
        this.tokenService = tokenService;
        this.eventPublisher = eventPublisher;
        this.rolesService = rolesService;
        this.encoder = encoder;
    }

    @RequestMapping("/sign-up")
    public ModelAndView registrationForm() {
        return new ModelAndView("register", "accountDto", new AccountRegDto());
    }


    @RequestMapping("/register/user")
    public ModelAndView registerUser(
            @Valid final AccountRegDto accountDto,
            final BindingResult result,
            HttpSession session,
            final HttpServletRequest request
    ) {
        if (result.hasErrors()) {
            return new ModelAndView("register", "accountDto", accountDto);
        }
        try {


            UserInfo user = new UserInfo();
            user.setEnabled(false);
            UserRole role = rolesService.getByValue("ROLE_USER");

            user.setUserRole(role);
            user.setPassword(encoder.encode(accountDto.getPassword()));
            user.setEmail(accountDto.getEmail());
            userInfoService.create(user);


            String sessionToken = (String) session.getAttribute("theToken");

            final String token;
            if (sessionToken == null) {
                 token = UUID.randomUUID().toString();
            }else {
                token = sessionToken;
            }


            tokenService.saveToken(token, user);


            final String appURL = "http://" + request.getServerName() + ":" + request.getServerPort() + ":" + request.getContextPath();
            sendVerificationEmail(user, token, appURL);
        } catch (EmailExistsExeption e) {
            result.addError(new FieldError("user", "email", e.getMessage()));
            return new ModelAndView("register", "user", accountDto);
        }
        return new ModelAndView("redirect:/login");
    }



    private void sendVerificationEmail(UserInfo user, String token, String appURL) {
        eventPublisher.publishEvent(new OnCreateAccountEvent(appURL, user, token));
    }


    @RequestMapping(value = "/registrationconfirm")
    public ModelAndView confirmRegistration(
            @RequestParam("token") String token,
            RedirectAttributes redirectAttributes) {
        try {
            Token verificationToken = tokenService.findByToken(token);
            UserInfo user = verificationToken.getUser();
            user.setEnabled(true);
            userInfoService.update(user);
            redirectAttributes.addFlashAttribute("message", "Your account verified successfully");
//        tokenService.delete(token); TODO
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/login");
    }


}
