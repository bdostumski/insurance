//package com.insurance.application.controllers.mvc.auth;
//
//import com.insurance.application.models.dtos.AccountRegDto;
//import com.insurance.application.models.dtos.InitialInfoStringDto;
//import com.insurance.application.services.UserInfoService;
//import com.insurance.application.services.UserRolesService;
//import com.insurance.application.services.VerificationTokenService;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpSession;
//
//
//@Controller
//@RequestMapping("/register")
//public class RegistrationWithAnonimousInfoController {
//
//    UserInfoService userInfoService;
//    VerificationTokenService tokenService;
//    ApplicationEventPublisher eventPublisher;
//    UserRolesService rolesService;
//    PasswordEncoder encoder;
//
//    public RegistrationWithAnonimousInfoController(UserInfoService userInfoService,
//                                                   VerificationTokenService tokenService,
//                                                   ApplicationEventPublisher eventPublisher,
//                                                   UserRolesService rolesService,
//                                                   PasswordEncoder encoder) {
//        this.userInfoService = userInfoService;
//        this.tokenService = tokenService;
//        this.eventPublisher = eventPublisher;
//        this.rolesService = rolesService;
//        this.encoder = encoder;
//    }
//
//    @PostMapping
//    public ModelAndView registrationForm(
//            final InitialInfoStringDto stringDto,
//            HttpSession session
//    ) {
//        AccountRegDto accountRegDto = new AccountRegDto();
//
//        accountRegDto.setReceivedTokenValue(token);
//
//        return new ModelAndView("register", "accountDto", accountRegDto );
//    }
//
//}
