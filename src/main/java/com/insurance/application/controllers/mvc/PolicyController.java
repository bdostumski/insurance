package com.insurance.application.controllers.mvc;

import com.insurance.application.models.*;
import com.insurance.application.models.dtos.InitialInfoStringDto;
import com.insurance.application.models.dtos.InitialPolicyDto;
import com.insurance.application.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import java.security.Principal;



@Controller
@RequestMapping("/policy")
public class PolicyController {

    InfoDtoService infoDtoService;
    UserInfoService userService;
    PolicyService policyService;
    CarService carService;
    CarModelService carModelService;
    CarBrandService carBrandService;


    @Autowired
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


    @GetMapping
    public String getPolicy(
            Model model,
            Principal principal
    ) {
        try {
            String tokenValue = userService.getByEmail(principal.getName()).getToken().getTokenValue();
            InitialInfoStringDto infoDto = infoDtoService.getByTokenValue(tokenValue);

            model.addAttribute("infoDto", infoDto);
            model.addAttribute("policyInfoDto", new InitialPolicyDto());

            return "policy";
        } catch (Exception e) {
            e.printStackTrace();
            return "rerirect:/";
        }

    }

    @PostMapping
    public String createPolicy(
            Principal principal,
            final InitialPolicyDto initialPolicyDto,
            HttpSession session
    ) {

        try {
            String tokenValue = (String) session.getAttribute("theToken");
            InitialInfoStringDto stringDto = infoDtoService.getByTokenValue(tokenValue);


            UserInfo user = configureUserDetails(principal, initialPolicyDto, stringDto);
            userService.update(user);

            Car car = enlistCar(stringDto, carBrandService, carModelService, user);
            carService.create(car);

            Policy policy = generatePolicy(initialPolicyDto, stringDto, user, car);
            policyService.create(policy);
            return "profile";

        } catch (Exception e) {
            e.printStackTrace();
            return "rerirect:/";
        }

    }

    private Policy generatePolicy(InitialPolicyDto initialPolicyDto, InitialInfoStringDto stringDto, UserInfo user, Car car) {
        Policy policy = new Policy();
        policy.setApproval((byte) 0);
        policy.setCar(car);
        policy.setUserInfo(user);
        policy.setTotalPrice(stringDto.getTotalPrice());
        policy.setStartDateTime(initialPolicyDto.getStartDate());
        return policy;
    }


    private UserInfo configureUserDetails(Principal principal, InitialPolicyDto initialPolicyDto, InitialInfoStringDto stringDto) {
        UserInfo user = userService.getByEmail(principal.getName());
        user.setBirthdate(stringDto.getDriverBirthDate());
        user.setFirstname(initialPolicyDto.getFirstName());
        user.setLastname(initialPolicyDto.getLastName());
        return user;
    }


    private Car enlistCar(InitialInfoStringDto stringDto, CarBrandService carBrandService, CarModelService carModelService, UserInfo user) {
        Car car = new Car();
        CarModel model = carModelService.getByModelName(stringDto.getCarModel());
        car.setUserInfo(user);
        car.setCarModel(model);
        car.setRegDate(stringDto.getRegistrationDate());
        car.setCubicCap(Double.parseDouble(stringDto.getCarCubic()));

        return car;
    }


}