package com.insurance.application.controllers.rest;


import com.insurance.application.models.Policy;
import com.insurance.application.services.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v.1.0/api/policy")
public class PolicyRestController {

    private final PolicyService policyService;

    @Autowired
    public PolicyRestController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping
    public List<Policy> getAll() {
        return policyService.getAllPolicies();
    }
}
