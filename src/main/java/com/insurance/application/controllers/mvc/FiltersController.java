package com.insurance.application.controllers.mvc;

import com.insurance.application.models.Policy;
import com.insurance.application.services.PolicyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/filters")
public class FiltersController {

    PolicyService policyService;

    public FiltersController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping
    public String getFilters(
            Model model
    ) {
        List<Policy> policyList = policyService.getAllPolicies();
        model.addAttribute("policyList", policyList);
        return "filters";
    }
}