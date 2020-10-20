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
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user-filter")
public class FilterUserController {

    PolicyService policyService;
    UserInfoService userInfoService;

    @Autowired
    public FilterUserController(PolicyService policyService, UserInfoService userInfoService) {

        this.policyService = policyService;
        this.userInfoService = userInfoService;
    }

    @GetMapping
    public String getFilters (Model model, Principal principal) {

        List<Policy> policyList = policyService.getByUserMail(principal.getName());

        model.addAttribute("loggedUser", isPrincipalNull(principal));
        model.addAttribute("policyList", policyList);

        return "/user-filter";
    }

    private UserInfo isPrincipalNull(Principal principal) {
        if(principal != null) {
            return userInfoService.getByEmail(principal.getName());
        } else {
            return new UserInfo();
        }
    }

    @GetMapping("/withdraw")
    public String withdraw(@RequestParam int id,
                               Model model,
                               Principal principal) {

        List<Policy> policyList = policyService.getByUserMail(principal.getName());
        Policy policy = policyService.getById(id);
        policy.setApproval((byte) 2);
        policyService.update(policy);

        model.addAttribute("loggedUser", isPrincipalNull(principal));
        model.addAttribute("policyList", policyList);

        return "redirect:/user-filter";
    }
}