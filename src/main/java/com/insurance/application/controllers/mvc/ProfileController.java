package com.insurance.application.controllers.mvc;

import com.insurance.application.models.dtos.InitialInfoStringDto;
import com.insurance.application.services.InfoDtoService;
import com.insurance.application.services.UserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    InfoDtoService infoDtoService;
    UserInfoService userService;

    public ProfileController(InfoDtoService infoDtoService, UserInfoService userService) {
        this.infoDtoService = infoDtoService;
        this.userService = userService;
    }

    @GetMapping
    public String getProfile(
            Model model,
            Principal principal
    ) {

       InitialInfoStringDto infoDto = infoDtoService.getByTokenValue(
               userService.getByEmail(principal.getName()).getToken().getTokenValue());

        model.addAttribute("infoDto", infoDto);

        return "profile";
    }
}