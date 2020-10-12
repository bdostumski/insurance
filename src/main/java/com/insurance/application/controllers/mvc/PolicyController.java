package com.insurance.application.controllers.mvc;

import com.insurance.application.models.dtos.InitialInfoDto;
import com.insurance.application.models.dtos.InitialInfoStringDto;
import com.insurance.application.services.InfoDtoService;
import com.insurance.application.services.UserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.ParseException;
import java.util.UUID;

import static com.insurance.application.models.mappers.InitialStringMapper.initialStringMapper;

@Controller
@RequestMapping("/policy")
public class PolicyController {

    InfoDtoService infoDtoService;
    UserInfoService userService;

    public PolicyController(InfoDtoService infoDtoService, UserInfoService userService) {
        this.infoDtoService = infoDtoService;
        this.userService = userService;
    }

    @GetMapping
    public String getPolicy(
            Model model,
            Principal principal
    ) {

        InitialInfoStringDto infoDto = infoDtoService.getByTokenValue(
                userService.getByEmail(principal.getName()).getToken().getTokenValue());

        model.addAttribute("infoDto", infoDto);

        return "policy";
    }

}