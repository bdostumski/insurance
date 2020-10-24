package com.insurance.application.controllers.mvc;

import com.insurance.application.models.Policy;
import com.insurance.application.models.UserInfo;
import com.insurance.application.models.dtos.PolicyFilterDto;
import com.insurance.application.services.FilterService;
import com.insurance.application.services.PolicyService;
import com.insurance.application.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user-filter")
public class FilterUserController {

    PolicyService policyService;
    UserInfoService userInfoService;
    FilterService filterService;

    @Autowired
    public FilterUserController(PolicyService policyService,
                                UserInfoService userInfoService,
                                FilterService filterService) {

        this.policyService = policyService;
        this.userInfoService = userInfoService;
        this.filterService = filterService;
    }

    @GetMapping
    public String getFilters (Model model, Principal principal) {

        List<Policy> policyList = policyService.getByUserMail(principal.getName());

        model.addAttribute("policyFilter", new PolicyFilterDto());
        model.addAttribute("loggedUser", isPrincipalNull(principal));
        model.addAttribute("policyList", policyList);

        return "/user-filter";
    }

    @GetMapping("/profile")
    public String policyProfile(Principal principal) {
        if(userInfoService.getByEmail(principal.getName()).getFirstname() != null) {
            return "redirect:/profile";
        }
        // TODO error message
        return "redirect:/user-filter";
    }

    @PostMapping
    public String filter (@ModelAttribute PolicyFilterDto policyFilterDto,
                          Model model,
                          Principal principal) {

        UserInfo user = userInfoService.getByEmail(principal.getName());

        model.addAttribute("policyFilter", new PolicyFilterDto());
        model.addAttribute("loggedUser", isPrincipalNull(principal));
        model.addAttribute("policyList",
                filterService.filterForUser (user.getId(),
                        policyFilterDto.getFromDate(),
                        policyFilterDto.getToDate()));

        return "user-filter";
    }

    @GetMapping("/withdraw")
    public String withdraw(@RequestParam int id,
                               Model model,
                               Principal principal) {

        List<Policy> policyList = policyService.getByUserMail(principal.getName());
        Policy policy = policyService.getById(id);
        policy.setApproval((byte) 2);
        policyService.update(policy);

        model.addAttribute("policyFilter", new PolicyFilterDto());
        model.addAttribute("loggedUser", isPrincipalNull(principal));
        model.addAttribute("policyList", policyList);

        return "redirect:/user-filter";
    }

    private UserInfo isPrincipalNull(Principal principal) {
        if(principal != null) {
            return userInfoService.getByEmail(principal.getName());
        } else {
            return new UserInfo();
        }
    }
}