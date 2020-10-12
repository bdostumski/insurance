package com.insurance.application.controllers.mvc;

import com.insurance.application.models.dtos.InitialInfoDto;
import com.insurance.application.models.dtos.InitialInfoStringDto;
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

    @GetMapping
    public String getPolicy() {
        return "policy";
    }

    @PostMapping
    public String createPolicy(
            InitialInfoStringDto initialInfoStringDto,
            BindingResult bindingResult,
            Model model,
            Principal principal
    ) {

        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }


        model.addAttribute("initialInfoStringDto", initialInfoStringDto);

        return "policy";
    }

}