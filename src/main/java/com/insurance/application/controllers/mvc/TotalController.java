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
import java.util.UUID;

import static com.insurance.application.models.mappers.InitialStringMapper.initialStringMapper;
import static com.insurance.application.utils.CalcUtil.*;
import static com.insurance.application.utils.ConvertDate.convertDate;
import static com.insurance.application.utils.ConvertDate.dateFormat;

@Controller
@RequestMapping("/total")
public class TotalController {

    CarBrandService carBrandService;
    CarModelService carModelService;
    BaseAmountService baseAmountService;
    CoefficientService coefficientService;
    UserInfoService userService;
    InfoDtoService infoDtoService;
    PolicyPaymentService policyPaymentService;

    @Autowired
    public TotalController (CarBrandService carBrandService,
                           CarModelService carModelService,
                           BaseAmountService baseAmountService,
                           CoefficientService coefficientService,
                           UserInfoService userService,
                           InfoDtoService infoDtoService,
                            PolicyPaymentService policyPaymentService) {
        this.carBrandService = carBrandService;
        this.carModelService = carModelService;
        this.baseAmountService = baseAmountService;
        this.coefficientService = coefficientService;
        this.userService = userService;
        this.infoDtoService = infoDtoService;
        this.policyPaymentService = policyPaymentService;
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


        model.addAttribute("loggedUser", Validator.loadUser(principal, userService));

        String tokenValue = generateToken(principal);
        double totalPremium = totalPremium(initialInfoDto);

        InitialInfoStringDto initialInfoStringDto = new InitialInfoStringDto();
        InitialInfoStringDto infoStringDto = initialStringMapper(initialInfoStringDto,
                carBrandService, carModelService, initialInfoDto, tokenValue, totalPremium);
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

    private double totalPremium(InitialInfoDto dto) {

        boolean hasAccident = dto.getHasAccidents();
        boolean isDriverUnderLimitAge = isDriverUnderAge(dto.getDriverBirthDate(), coefficientService);

        int carAge = calcAge(dateFormat(dto.getRegistrationDate()));
        int carCubic = getCarCubic(dto.getCarCubic());

        double taxAmount = coefficientService.getById(1).getTaxAmount();
        double baseAmount = baseAmountService.getBaseAmount(carCubic, carAge);
        double accidentCoefficient = coefficientService.getById(1).getAccident();
        double driverAgeCoefficient =  coefficientService.getById(1).getAgeCoefficient();
        double netPremium = policyPaymentService.netPremium(hasAccident, isDriverUnderLimitAge,
                baseAmount, accidentCoefficient, driverAgeCoefficient);

        double totalPremium = policyPaymentService.totalPremium(netPremium, taxAmount);
        return totalPremium;
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