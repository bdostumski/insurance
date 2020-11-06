package com.insurance.application.controllers.mvc;

import com.insurance.application.models.UserInfo;
import com.insurance.application.models.dtos.InitialInfoDto;
import com.insurance.application.models.dtos.InitialInfoStringDto;
import com.insurance.application.services.*;
import com.insurance.application.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;

import static com.insurance.application.utils.Constants.*;

@Controller
@RequestMapping("/")
public class HomePageController {

    UserInfoService userInfoService;
    InfoDtoService infoDtoService;
    ApplicationEventPublisher eventPublisher;
    CarBrandService carBrandService;
    CarModelService carModelService;
    CarService carService;

    @Autowired
    public HomePageController(UserInfoService userInfoService,
                              InfoDtoService infoDtoService,
                              ApplicationEventPublisher eventPublisher,
                              CarBrandService carBrandService,
                              CarModelService carModelService,
                              CarService carService) {
        this.userInfoService = userInfoService;
        this.infoDtoService = infoDtoService;
        this.eventPublisher = eventPublisher;
        this.carBrandService = carBrandService;
        this.carModelService = carModelService;
        this.carService = carService;
    }

    @GetMapping
    public String getIndex(Model model, HttpSession session, Principal principal) {

        UserInfo userInfo = Validator.loadUser(principal, userInfoService);
        model.addAttribute("loggedUser", userInfo);

        if (isInfoStringDtoAvailable(principal)) {
            return "redirect:policy";
        }

        if (principal != null) {

            if (checkUserHasRole(principal, USER_ROLE_ADMIN))
                return "redirect:/admin-filter";

            if (checkUserHasRole(principal, USER_ROLE_AGENT)) {
                return "redirect:/agent-filter";
            }

            if (session.getAttribute(TOKEN) != null) {
                assignInitialnfoDtoToUser(session, principal);
                return "redirect:policy";
            }
        }

        model.addAttribute("brands", carBrandService.getAll());
        model.addAttribute("models", carModelService.getAll());
        model.addAttribute("initialInfoDto", new InitialInfoDto());

        return "index";
    }

    /**
     * Checks if initial policy info is available for the logged in user.
     *
     * @param principal - currently logged in user
     * @return boolean
     */
    private boolean isInfoStringDtoAvailable(Principal principal) {
        try {
            UserInfo user = userInfoService.getByEmail(principal.getName());
            infoDtoService.getByTokenValue(user.getToken().getTokenValue());
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    private boolean checkUserHasRole(Principal principal, int roleToCheck) {
        return userInfoService.getByEmail(principal.getName()).getUserRole().getId() == roleToCheck;
    }

    private void assignInitialnfoDtoToUser(HttpSession session, Principal principal) {
        String token = (String) session.getAttribute(TOKEN);
        InitialInfoStringDto initialInfoStringDto = infoDtoService.getByTokenValue(token);
        UserInfo user = userInfoService.getByEmail(principal.getName());
        String userToken = user.getToken().getTokenValue();
        initialInfoStringDto.setUserToken(userToken);
        infoDtoService.update(initialInfoStringDto);
        session.removeAttribute(TOKEN);
    }
}
