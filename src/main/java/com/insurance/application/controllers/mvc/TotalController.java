package com.insurance.application.controllers.mvc;

import com.insurance.application.models.dtos.AccountRegDto;
import com.insurance.application.models.dtos.InitialInfoDto;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/total")
public class TotalController {

    @GetMapping
    public String getTotal() {
        return "total";
    }

}