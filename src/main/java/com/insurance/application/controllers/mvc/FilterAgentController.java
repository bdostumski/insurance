package com.insurance.application.controllers.mvc;

import com.insurance.application.models.Policy;
import com.insurance.application.models.UserInfo;
import com.insurance.application.services.PolicyService;
import com.insurance.application.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/agent-filter")
public class FilterAgentController {

    PolicyService policyService;
    UserInfoService userInfoService;

    @Autowired
    public FilterAgentController(PolicyService policyService, UserInfoService userInfoService) {
        this.policyService = policyService;
        this.userInfoService = userInfoService;
    }

    @GetMapping
    public String getFilters(Model model, Principal principal) {

        List<Policy> policyList = policyService.getAllPolicies();
        model.addAttribute("loggedUser", isPrincipalNull(principal));
        model.addAttribute("policyList", policyList);

        return "/agent-filter";
    }

    private UserInfo isPrincipalNull(Principal principal) {
        if(principal != null) {
            return userInfoService.getByEmail(principal.getName());
        } else {
            return new UserInfo();
        }
    }
}