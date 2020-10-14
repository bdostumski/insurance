package com.insurance.application.controllers.mvc;

import com.insurance.application.models.UserInfo;
import com.insurance.application.models.dtos.InitialInfoDto;
import com.insurance.application.services.InfoDtoService;
import com.insurance.application.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class IndexController {

    UserInfoService userInfoService;
    InfoDtoService infoDtoService;

    @Autowired
    public IndexController(UserInfoService userInfoService, InfoDtoService infoDtoService) {
        this.userInfoService = userInfoService;
        this.infoDtoService = infoDtoService;
    }

    @GetMapping
    public String getIndex(Model model,
                           Principal principal) {

        if (principal == null || isInfoStringDtoNotAvailable(principal)) {

            model.addAttribute("initialInfoDto", new InitialInfoDto());
            return "index";

        } else {
            return "redirect:/policy";
        }
    }

    private boolean isInfoStringDtoNotAvailable(Principal principal){
        UserInfo user = userInfoService.getByEmail(principal.getName());
        return infoDtoService.getByTokenValue(user.getToken().getTokenValue()) == null;
    }
}
