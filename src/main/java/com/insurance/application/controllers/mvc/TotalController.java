package com.insurance.application.controllers.mvc;

import com.insurance.application.models.UserInfo;
import com.insurance.application.models.dtos.InitialInfoDto;
import com.insurance.application.models.dtos.InitialInfoStringDto;
import com.insurance.application.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.UUID;

import static com.insurance.application.models.mappers.InitialStringMapper.initialStringMapper;

@Controller
@RequestMapping("/total")
public class TotalController {

    CarBrandService carBrandService;
    CarModelService carModelService;
    BaseAmountService baseAmountService;
    CoefficientService coefficientService;
    UserInfoService userService;

    @Autowired
    public TotalController(CarBrandService carBrandService, CarModelService carModelService,
                           BaseAmountService baseAmountService, CoefficientService coefficientService,
                           UserInfoService userService) {
        this.carBrandService = carBrandService;
        this.carModelService = carModelService;
        this.baseAmountService = baseAmountService;
        this.coefficientService = coefficientService;
        this.userService = userService;
    }

    @GetMapping
    public String getTotalPage() {
        return "redirect:/";
    }

    @PostMapping
    public String createOffer(
            final InitialInfoDto initialInfoDto,
            BindingResult bindingResult,
            Model model,
            Principal principal
    ) {

        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }

        try {
            final String tokenValue = UUID.randomUUID().toString();

            InitialInfoStringDto initialInfoStringDto = new InitialInfoStringDto();
            InitialInfoStringDto infoStringDto = initialStringMapper(initialInfoStringDto, carBrandService,
                    carModelService, baseAmountService, coefficientService, initialInfoDto, tokenValue);
            model.addAttribute("initialInfoDto", infoStringDto);

        } catch (ParseException e) {
            e.printStackTrace();
            return "redirect:/";
        }

        return "total";
    }
}