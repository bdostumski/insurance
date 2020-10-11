package com.insurance.application.controllers.mvc;

import com.insurance.application.models.dtos.AccountRegDto;
import com.insurance.application.models.dtos.InitialInfoDto;
import com.insurance.application.services.BaseAmountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {


    @RequestMapping("/")
    public ModelAndView initialForm() {
        return new ModelAndView("index", "initialInfoDto", new InitialInfoDto());
    }


}
