package com.insurance.application.controllers.mvc;

import com.insurance.application.models.UserInfo;
import com.insurance.application.models.dtos.UserEditDto;
import com.insurance.application.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequestMapping("/edit")
public class EditController {

    UserInfoService userService;

    @Autowired
    public EditController(UserInfoService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getEditProfile(Model model, Principal principal) {
        UserInfo user = userService.getByEmail(principal.getName());
        model.addAttribute("userInfo", user);
        return "edit";
    }

    @PostMapping
    public String editUserProfile(
            final UserEditDto userEditDto,
            Model model
    ) {

        UserInfo userToEdit;

        userToEdit = userService.getByEmail(userEditDto.getEmail());
        userToEdit.setFirstname(userEditDto.getFirstname());
        userToEdit.setLastname(userEditDto.getLastname());
        userToEdit.setBirthdate(dateFormat(userEditDto.getBirthdate()));
        userToEdit.setEmail(userEditDto.getEmail());
        userToEdit.setPhoneNumber(userEditDto.getPhoneNumber());
        userToEdit.setAddress(userEditDto.getAddress());
        userService.update(userToEdit);

        model.addAttribute("uerInfo", userToEdit);
        return "redirect:/profile";

    }

    private static LocalDate dateFormat(String date) {
        return LocalDate.parse(date);
    }
}
