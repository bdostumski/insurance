package com.insurance.application.controllers.mvc;

import com.insurance.application.models.dtos.InitialInfoDto;
import com.insurance.application.models.dtos.InitialInfoStringDto;
import com.insurance.application.services.BaseAmountService;
import com.insurance.application.services.CarBrandService;
import com.insurance.application.services.CarModelService;
import com.insurance.application.services.CoefficientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

import static com.insurance.application.models.mappers.InitialStringMapper.initialStringMapper;

@Controller
@RequestMapping("/total")
public class TotalController {

    CarBrandService carBrandService;
    CarModelService carModelService;
    BaseAmountService baseAmountService;
    CoefficientService coefficientService;

    @Autowired
    public TotalController(CarBrandService carBrandService,
                           CarModelService carModelService,
                           BaseAmountService baseAmountService,
                           CoefficientService coefficientService) {
        this.carBrandService = carBrandService;
        this.carModelService = carModelService;
        this.baseAmountService = baseAmountService;
        this.coefficientService = coefficientService;
    }

    @PostMapping
    public String createOffer(
            final InitialInfoDto initialInfoDto,
            BindingResult bindingResult,
            Model model
    ) {

        if(bindingResult.hasErrors()) {
            return "redirect:/";
        }

        try {

            InitialInfoStringDto initialInfoStringDto = new InitialInfoStringDto();
            InitialInfoStringDto infoStringDto = initialStringMapper(initialInfoStringDto, carBrandService,
                    carModelService, baseAmountService, coefficientService, initialInfoDto);
            model.addAttribute("initialInfoDto", infoStringDto);

        } catch (ParseException e) {
            e.printStackTrace();
            return "redirect:/";
        }

        return "total";
    }
}