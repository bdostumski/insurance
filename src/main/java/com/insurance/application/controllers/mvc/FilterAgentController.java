package com.insurance.application.controllers.mvc;

import com.insurance.application.models.Policy;
import com.insurance.application.models.UserInfo;
import com.insurance.application.models.dtos.PolicyFilterDto;
import com.insurance.application.services.FilterService;
import com.insurance.application.services.PolicyService;
import com.insurance.application.services.UserInfoService;
import com.insurance.application.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.insurance.application.utils.Constants.APPROVAL_STATUS_APPROVED;
import static com.insurance.application.utils.Constants.APPROVAL_STATUS_DECLINED;

@Controller
@RequestMapping("/agent-filter")
public class FilterAgentController {

    PolicyService policyService;
    UserInfoService userInfoService;
    FilterService filterService;

    @Autowired
    public FilterAgentController(PolicyService policyService,
                                 UserInfoService userInfoService,
                                 FilterService filterService) {
        this.policyService = policyService;
        this.userInfoService = userInfoService;
        this.filterService = filterService;
    }

    @GetMapping
    public String getFilters(Model model, Principal principal) {

        List<Policy> policyList = policyService.getAllPolicies();

        model.addAttribute("policyFilter", new PolicyFilterDto());
        model.addAttribute("loggedUser", Validator.loadUser(principal, userInfoService));
        model.addAttribute("policyList", policyList);

        return "/agent-filter";
    }

    @PostMapping
    public String filter(@ModelAttribute PolicyFilterDto policyFilterDto,
                         Model model,
                         Principal principal) {

        model.addAttribute("policyFilter", new PolicyFilterDto());
        model.addAttribute("loggedUser", Validator.loadUser(principal, userInfoService));
        model.addAttribute("policyList",
                filterService.filterForAgent(policyFilterDto.getFromDate(),
                        policyFilterDto.getToDate(),
                        policyFilterDto.getMail()));

        return "agent-filter";
    }

    @GetMapping("/approve")
    public String approve(@RequestParam int id,
                          Model model,
                          Principal principal) {

        updatePolicyStatus(id, model, principal, APPROVAL_STATUS_APPROVED);

        return "redirect:/agent-filter";
    }

    @GetMapping("/decline")
    public String decline(@RequestParam int id,
                          Model model,
                          Principal principal) {

        updatePolicyStatus(id, model, principal, APPROVAL_STATUS_DECLINED);

        return "redirect:/agent-filter";
    }

    private void updatePolicyStatus(int policyId, Model model, Principal principal, byte policyStatus) {
        List<Policy> policyList = policyService.getAllPolicies();
        Policy policy = policyService.getById(policyId);
        policy.setApproval(policyStatus);
        policyService.update(policy);

        model.addAttribute("loggedUser", Validator.loadUser(principal, userInfoService));
        model.addAttribute("policyList", policyList);
    }
}