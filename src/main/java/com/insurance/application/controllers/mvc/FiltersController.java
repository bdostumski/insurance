package com.insurance.application.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/filters")
public class FiltersController {

    @GetMapping
    public String getFilters() {
        return "filters";
    }
}