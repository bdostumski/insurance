package com.insurance.application.controllers.mvc;

import com.insurance.application.models.Car;
import com.insurance.application.models.Policy;
import com.insurance.application.models.UserInfo;
import com.insurance.application.models.dtos.InitialInfoDto;
import com.insurance.application.models.dtos.InitialInfoStringDto;
import com.insurance.application.models.dtos.InitialPolicyDto;
import com.insurance.application.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.text.ParseException;
import java.util.UUID;

import static com.insurance.application.models.mappers.InitialStringMapper.initialStringMapper;

@Controller
@RequestMapping("/policy")
public class PolicyController {

    InfoDtoService infoDtoService;
    UserInfoService userService;
    PolicyService policyService;
    CarService carService;
    CarModelService carModelService;
    CarBrandService carBrandService;

    public PolicyController(InfoDtoService infoDtoService, UserInfoService userService,
                            PolicyService policyService, CarService carService,
                            CarModelService carModelService, CarBrandService carBrandService) {
        this.infoDtoService = infoDtoService;
        this.userService = userService;
        this.policyService = policyService;
        this.carService = carService;
        this.carModelService = carModelService;
        this.carBrandService = carBrandService;
    }

    @Autowired


    @GetMapping
    public String getPolicy(
            Model model,
            Principal principal

    ) {

        String tokenValue = userService.getByEmail(principal.getName()).getToken().getTokenValue();
        InitialInfoStringDto infoDto = infoDtoService.getByTokenValue(tokenValue);

        model.addAttribute("infoDto", infoDto);
        model.addAttribute("policyInfoDto", new InitialPolicyDto());

        return "policy";
    }

    @PostMapping
    public String createPolicy(
            Model model,
            Principal principal,
            final InitialPolicyDto initialPolicyDto,
            HttpSession session
    ){

        String tokenValue = (String) session.getAttribute("theToken");
        InitialInfoStringDto stringDto = infoDtoService.getByTokenValue(tokenValue);
        Policy policy = new Policy();

        Car car = new Car();
        car.setCarBrand();
        car.setCubicCap();
        car.setRegDate();
        car.setUserInfo();

        UserInfo user = userService.getByEmail(principal.getName());
        user.setBirthdate(stringDto.getDriverBirthDate());
        user.setFirstname(initialPolicyDto.getFirstName());
        user.setLastname(initialPolicyDto.getLastName());




        policy.setApproval((byte) 0);
        policy.setCar(car);



    }

}