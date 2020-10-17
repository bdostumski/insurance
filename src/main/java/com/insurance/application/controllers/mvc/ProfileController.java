package com.insurance.application.controllers.mvc;

import com.insurance.application.models.UserInfo;
import com.insurance.application.models.dtos.UserEditDto;
import com.insurance.application.services.InfoDtoService;
import com.insurance.application.services.UserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    InfoDtoService infoDtoService;
    UserInfoService userService;

    public ProfileController(InfoDtoService infoDtoService, UserInfoService userService) {
        this.infoDtoService = infoDtoService;
        this.userService = userService;
    }

    @GetMapping
    public String getProfile (Model model, Principal principal) {

        UserInfo user = userService.getByEmail(principal.getName());
        model.addAttribute("uerInfo", user);

        return "profile";
    }

    @PostMapping("/update-user")
    public String updateUserProfile (final UserEditDto userEditDto, Model model) {

        UserInfo userToEdit;

        userToEdit = userService.getByEmail(userEditDto.getEmail());
        userToEdit.setFirstname(userEditDto.getFirstname());
        userToEdit.setLastname(userEditDto.getLastname());
        userToEdit.setBirthdate(userEditDto.getBirthdate());
        userToEdit.setEmail(userEditDto.getEmail());
        userToEdit.setPhoneNumber(userEditDto.getPhoneNumber());
        userToEdit.setAddress(userEditDto.getAddress());
        userService.update(userToEdit);

        model.addAttribute("uerInfo", userToEdit);

        return "redirect:/profile";
    }

    @GetMapping("/update-user")
    public String getUpdateUserProfile(Model model, Principal principal) {

        UserInfo user = userService.getByEmail(principal.getName());
        model.addAttribute("userInfo", user);

        return "edit";
    }
}