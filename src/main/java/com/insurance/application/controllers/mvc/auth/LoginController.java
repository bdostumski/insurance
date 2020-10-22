package com.insurance.application.controllers.mvc.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String getLogin(RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("message", "Successfully");

        return "login";
    }
}