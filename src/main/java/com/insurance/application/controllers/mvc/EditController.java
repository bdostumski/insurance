package com.insurance.application.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/edit")
public class EditController {

    @GetMapping
    public String getEditProfile() {
        return "edit";
    }
}
