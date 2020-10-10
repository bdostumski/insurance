package com.insurance.application.controllers.mvc;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PasswordResetController {

    @GetMapping("/resetpassword")
    public String getLogin() {
        return "resetpassword";
    }

}
