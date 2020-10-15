package com.insurance.application.controllers.mvc;

import com.insurance.application.models.CarModel;
import com.insurance.application.models.dtos.InitialInfoDto;
import com.insurance.application.models.dtos.InitialInfoStringDto;
import com.insurance.application.services.*;
import com.insurance.application.utils.OnCreateAccountEvent;
import com.insurance.application.utils.OnRequestModelListener;
import com.insurance.application.utils.OnRequestModelsEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.text.ParseException;
import java.util.List;
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


    @Autowired
    public TotalController(CarBrandService carBrandService, CarModelService carModelService,
                           BaseAmountService baseAmountService,
                           CoefficientService coefficientService,
                           UserInfoService userService, InfoDtoService infoDtoService) {
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

    @PostMapping
    public String createOffer(
            final InitialInfoDto initialInfoDto,
            BindingResult bindingResult,
            Model model,
            HttpSession session,
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
                String userMail = principal.getName();
                tokenValue = userService.getByEmail(userMail).getToken().getTokenValue();
            }

            InitialInfoStringDto initialInfoStringDto = new InitialInfoStringDto();
            InitialInfoStringDto infoStringDto = initialStringMapper(initialInfoStringDto, carBrandService,
                    carModelService, baseAmountService, coefficientService, initialInfoDto, tokenValue);

            infoDtoService.create(infoStringDto);

            session.setAttribute("theToken", tokenValue);

            model.addAttribute("initialInfoDto", infoStringDto);

        } catch (ParseException e) {
            e.printStackTrace();
            return "redirect:/";
        }

        return "total";
    }

}