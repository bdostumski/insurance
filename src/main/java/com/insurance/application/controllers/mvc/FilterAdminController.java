package com.insurance.application.controllers.mvc;

import com.insurance.application.models.UserInfo;
import com.insurance.application.models.dtos.UserFilterDto;
import com.insurance.application.services.FilterService;
import com.insurance.application.services.PolicyService;
import com.insurance.application.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin-filter")
public class FilterAdminController {

    PolicyService policyService;
    UserInfoService userInfoService;
    FilterService filterService;

    @Autowired
    public FilterAdminController(PolicyService policyService,
                                 UserInfoService userInfoService,
                                 FilterService filterService) {
        this.policyService = policyService;
        this.userInfoService = userInfoService;
        this.filterService = filterService;
    }

    @GetMapping
    public String getAdminPage(Principal principal, Model model) {

        model.addAttribute("userFilter", new UserFilterDto());
        model.addAttribute("loggedUser", isPrincipalNull(principal));
        model.addAttribute("users", userInfoService.getAll());

        return "/admin-filter";
    }

    @GetMapping("/new-agent")
    public String newAgent() {
        return "redirect:/new-agent";
    }

    @GetMapping("/approve")
    public String approve(@RequestParam int id,
                          Model model,
                          Principal principal) {

        UserInfo userInfo = userInfoService.getById(id);
        userInfo.setEnabled(true);
        userInfoService.update(userInfo);

        model.addAttribute("loggedUser", isPrincipalNull(principal));
        model.addAttribute("userInfo", new UserInfo());

        return "redirect:/admin-filter";
    }

    @GetMapping("/decline")
    public String decline(@RequestParam int id,
                          Model model,
                          Principal principal) {

        UserInfo userInfo = userInfoService.getById(id);
        userInfo.setEnabled(false);
        userInfoService.update(userInfo);

        model.addAttribute("loggedUser", isPrincipalNull(principal));
        model.addAttribute("userInfo", new UserInfo());

        return "redirect:/admin-filter";
    }

    @PostMapping
    public String filter (@ModelAttribute UserFilterDto userFilterDto,
                          Model model,
                          Principal principal) {

        model.addAttribute("userFilter", new UserFilterDto());
        model.addAttribute("loggedUser", isPrincipalNull(principal));
        model.addAttribute("users",
                filterService.filterForAdmin(userFilterDto.getFirstname(),
                        userFilterDto.getLastname(),
                        userFilterDto.getEmail()));

        return "admin-filter";
    }

    private UserInfo isPrincipalNull(Principal principal) {
        if(principal != null) {
            return userInfoService.getByEmail(principal.getName());
        } else {
            return new UserInfo();
        }
    }

}
