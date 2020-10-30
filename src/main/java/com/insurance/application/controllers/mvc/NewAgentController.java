package com.insurance.application.controllers.mvc;

import com.insurance.application.models.Policy;
import com.insurance.application.models.UserInfo;
import com.insurance.application.models.UserRole;
import com.insurance.application.models.dtos.UserProfileInfoDto;
import com.insurance.application.services.InfoDtoService;
import com.insurance.application.services.UserInfoService;
import com.insurance.application.services.UserRolesService;
import com.insurance.application.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

import static com.insurance.application.utils.Constants.AGENT_SECRET_PASSWORD;
import static com.insurance.application.utils.Constants.AUTHORITY_AGENT;

@Controller
@RequestMapping("/new-agent")
public class NewAgentController {

    InfoDtoService infoDtoService;
    UserInfoService userService;
    PasswordEncoder encoder;
    UserRolesService rolesService;

    @Autowired
    public NewAgentController(InfoDtoService infoDtoService, UserInfoService userService,
                              PasswordEncoder encoder, UserRolesService rolesService) {
        this.infoDtoService = infoDtoService;
        this.userService = userService;
        this.encoder = encoder;
        this.rolesService = rolesService;
    }

    @GetMapping
    public String getUpdateUserProfile(Model model, Principal principal) {

        model.addAttribute("loggedUser", Validator.loadUser(principal, userService));
        model.addAttribute("userInfo", new UserInfo());

        return "new-agent";
    }

    @PostMapping
    public String createAgent (final UserProfileInfoDto agetnDto, Model model) {

        UserInfo createAgent = new UserInfo();

        createAgent.setFirstname(agetnDto.getFirstname());
        createAgent.setLastname(agetnDto.getLastname());
        createAgent.setBirthdate(agetnDto.getBirthdate());
        createAgent.setEmail(agetnDto.getEmail());
        createAgent.setPhoneNumber(agetnDto.getPhoneNumber());
        createAgent.setAddress(agetnDto.getAddress());
        createAgent.setUserRole(rolesService.getByValue(AUTHORITY_AGENT));

        createAgent.setPassword(encoder.encode(AGENT_SECRET_PASSWORD));

        userService.create(createAgent);

        model.addAttribute("uerInfo", createAgent);

        return "redirect:/admin-filter";
    }
}
