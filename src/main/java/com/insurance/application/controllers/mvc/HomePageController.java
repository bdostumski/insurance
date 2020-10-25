package com.insurance.application.controllers.mvc;

import com.insurance.application.models.CarModel;
import com.insurance.application.models.UserInfo;
import com.insurance.application.models.dtos.InitialInfoDto;
import com.insurance.application.models.dtos.InitialInfoStringDto;
import com.insurance.application.services.*;
import com.insurance.application.events.OnRequestModelsEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

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
    public HomePageController (UserInfoService userInfoService,
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
    public String getIndex (Model model, HttpSession session, Principal principal) {

        /**
         * On login in index and total page, principal is not null
         * When I go to total and register new user, and login principal is null
         */

        UserInfo userInfo = isPrincipalNull(principal);
        model.addAttribute("loggedUser", userInfo);

        if(isInfoStringDtoAvailable(principal)) {
            return "redirect:policy";
        }

        if(principal != null) {

            if(userInfoService.getByEmail(principal.getName()).getUserRole().getId() == 1) {
                return "redirect:/admin-filter";
            }

            if(userInfoService.getByEmail(principal.getName()).getUserRole().getId() == 2) {
                return "redirect:/agent-filter";
            }

            if(session.getAttribute("theToken") != null) {
                String token = (String) session.getAttribute("theToken");
                InitialInfoStringDto initialInfoStringDto = infoDtoService.getByTokenValue(token);
                UserInfo user = userInfoService.getByEmail(principal.getName());
                String userToken = user.getToken().getTokenValue();
                initialInfoStringDto.setUserToken(userToken);
                infoDtoService.update(initialInfoStringDto);
                return "redirect:policy";
            }
        }

        model.addAttribute("brands", carBrandService.getAll());
        model.addAttribute("models", carModelService.getAll());
//        model.addAttribute("cars", carService.getById());
        model.addAttribute("initialInfoDto", new InitialInfoDto());

        return "index";
    }

    private UserInfo isPrincipalNull(Principal principal) {
        if(principal != null) {
            return userInfoService.getByEmail(principal.getName());
        } else {
            return new UserInfo();
        }
    }


    /*
    TODO - add coments!
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


    /*
    TODO -> resolve this issue with async. requests
     */
    @PostMapping("/brandmodels")
    public String getBrandModels(
            @RequestHeader("brandID") int brandId,
            Model model,
            HttpSession session
    ){

        System.out.println("Hi brand: " + brandId);

        eventPublisher.publishEvent(new OnRequestModelsEvent(brandId, session) );

        List<CarModel> carModels = (List<CarModel>)session.getAttribute("modelsList");

        for(CarModel c : carModels)
            System.out.println(c.getCarBrand());

        model.addAttribute("carmodels", carModels);

        return "index";
    }
}
