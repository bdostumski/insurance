package com.insurance.application.controllers.mvc;

import com.insurance.application.models.dtos.InitialInfoDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String getIndex(Model model) {
        model.addAttribute("initialInfoDto", new InitialInfoDto());
        return "index";
    }
}
