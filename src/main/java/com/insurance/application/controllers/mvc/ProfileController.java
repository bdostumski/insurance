package com.insurance.application.controllers.mvc;

import com.insurance.application.models.UserInfo;
import com.insurance.application.models.dtos.InitialInfoStringDto;
import com.insurance.application.services.InfoDtoService;
import com.insurance.application.services.UserInfoService;
import com.insurance.application.utils.ConvertDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.ParseException;
import java.time.LocalDate;

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
        UserInfo user = userService.getByEmail(principal.getName());
        model.addAttribute("uerInfo", user);
        return "profile";
    }

    @PostMapping("/update-user")
    public void updateUserInfo(
            @RequestParam UserInfo user
    ){


    }
}