package com.insurance.application.controllers.mvc.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gologout")
public class LogoutController {

    @GetMapping
    public String logoutForm() {
        return "login";
    }
}
