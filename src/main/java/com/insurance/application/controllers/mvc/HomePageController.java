package com.insurance.application.controllers.mvc;

import com.insurance.application.models.CarModel;
import com.insurance.application.models.UserInfo;
import com.insurance.application.models.dtos.InitialInfoDto;
import com.insurance.application.services.InfoDtoService;
import com.insurance.application.services.UserInfoService;
import com.insurance.application.utils.OnRequestModelsEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomePageController {

    UserInfoService userInfoService;
    InfoDtoService infoDtoService;
    ApplicationEventPublisher eventPublisher;

    @Autowired
    public HomePageController(UserInfoService userInfoService, InfoDtoService infoDtoService,
                              ApplicationEventPublisher eventPublisher) {
        this.userInfoService = userInfoService;
        this.infoDtoService = infoDtoService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping
    public String getIndex(Model model,
                           Principal principal) {

        if (principal == null || !isInfoStringDtoAvailable(principal)) {

            model.addAttribute("initialInfoDto", new InitialInfoDto());
            return "index";

        } else {
            return "redirect:/policy";
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
            exception.printStackTrace();
            return false;
        }
    }


/*
TODO -> resolve this issue with async. requests
 */
    @PostMapping("/brandmodels")
    public String getBrandModels(
            @RequestParam("brandID") int brandId,
            Model model,
            HttpSession sesssion
    ){
        eventPublisher.publishEvent(new OnRequestModelsEvent(brandId, sesssion) );

        List<CarModel> carModels = (List<CarModel>)sesssion.getAttribute("modelsList");

        model.addAttribute("carmodels", carModels);

        return "index";
    }
}
