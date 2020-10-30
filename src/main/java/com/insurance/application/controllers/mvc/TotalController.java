package com.insurance.application.controllers.mvc;

import com.insurance.application.models.UserInfo;
import com.insurance.application.models.dtos.InitialInfoDto;
import com.insurance.application.models.dtos.InitialInfoStringDto;
import com.insurance.application.services.*;
import com.insurance.application.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.text.ParseException;
import java.util.UUID;

import static com.insurance.application.models.mappers.InitialStringMapper.initialStringMapper;
import static com.insurance.application.utils.ConvertDate.convertDate;

@Controller
@RequestMapping("/total")
public class TotalController {

    CarBrandService carBrandService;
    CarModelService carModelService;
    BaseAmountService baseAmountService;
    CoefficientService coefficientService;
    UserInfoService userService;
    InfoDtoService infoDtoService;

    @Autowired
    public TotalController (CarBrandService carBrandService,
                           CarModelService carModelService,
                           BaseAmountService baseAmountService,
                           CoefficientService coefficientService,
                           UserInfoService userService,
                           InfoDtoService infoDtoService) {
        this.carBrandService = carBrandService;
        this.carModelService = carModelService;
        this.baseAmountService = baseAmountService;
        this.coefficientService = coefficientService;
        this.userService = userService;
        this.infoDtoService = infoDtoService;
    }

    @GetMapping
    public String getTotalPage() {
        return "redirect:/";
    }

    @GetMapping("/new-offer")
    public String getTotalPage(Principal principal) {
        deletePolicy(principal);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String policyLogout(Principal principal) {
        deletePolicy(principal);
        return "redirect:/logout";
    }

    @GetMapping("/profile")
    public String policyProfile(Principal principal) {
        deletePolicy(principal);
        return "redirect:/profile";
    }


    @PostMapping
    public String createOffer (final InitialInfoDto initialInfoDto,
                               Model model,
                               HttpSession session,
                               Principal principal) {

            String tokenValue = generateToken(principal);

            model.addAttribute("loggedUser", Validator.loadUser(principal, userService));

            InitialInfoStringDto initialInfoStringDto = new InitialInfoStringDto();
            InitialInfoStringDto infoStringDto = initialStringMapper (initialInfoStringDto,
                    carBrandService, carModelService, baseAmountService,
                    coefficientService, initialInfoDto, tokenValue);
            infoDtoService.create(infoStringDto);

            if (principal == null) {
                session.setAttribute("theToken", tokenValue);
            }

            String registrationDate = convertDate(initialInfoDto.getRegistrationDate());
            model.addAttribute("registrationDateModel", registrationDate);

            String driverBirthDate = convertDate(initialInfoDto.getDriverBirthDate());
            model.addAttribute("driverBirthDateModel", driverBirthDate);

            model.addAttribute("initialInfoDto", infoStringDto);

        return "total";
    }

    private String generateToken(Principal principal){

        if (principal == null) {
            return UUID.randomUUID().toString();
        } else {
            String userMail = principal.getName();
            return userService.getByEmail(userMail).getToken().getTokenValue();
        }
    }

    private void deletePolicy(Principal principal) {
        String tokenValue = userService.getByEmail(principal.getName()).getToken().getTokenValue();
        InitialInfoStringDto infoDto = infoDtoService.getByTokenValue(tokenValue);
        infoDtoService.delete(infoDto);
    }
}