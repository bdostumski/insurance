package com.insurance.application.controllers.mvc;

import com.insurance.application.models.dtos.InitialInfoDto;
import com.insurance.application.models.dtos.InitialInfoStringDto;
import com.insurance.application.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
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
    InfoDtoService infoDtoService;

    public TotalController(CarBrandService carBrandService, CarModelService carModelService,
                           BaseAmountService baseAmountService, CoefficientService coefficientService,
                           UserInfoService userService, InfoDtoService infoDtoService) {
        this.carBrandService = carBrandService;
        this.carModelService = carModelService;
        this.baseAmountService = baseAmountService;
        this.coefficientService = coefficientService;
        this.userService = userService;
        this.infoDtoService = infoDtoService;
    }

    @Autowired

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
            String tokenValue;
            if (principal == null) {
              tokenValue  = UUID.randomUUID().toString();
            }else {
                tokenValue = userService.getByEmail(principal.getName()).getToken().getTokenValue();
            }

            InitialInfoStringDto initialInfoStringDto = new InitialInfoStringDto();
            InitialInfoStringDto infoStringDto = initialStringMapper(initialInfoStringDto, carBrandService,
                    carModelService, baseAmountService, coefficientService, initialInfoDto, tokenValue);

            infoDtoService.create(infoStringDto);

            model.addAttribute("initialInfoDto", infoStringDto);

        } catch (ParseException e) {
            e.printStackTrace();
            return "redirect:/";
        }

        return "total";
    }
}