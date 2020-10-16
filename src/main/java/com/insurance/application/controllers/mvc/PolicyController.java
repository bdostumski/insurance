package com.insurance.application.controllers.mvc;

import com.insurance.application.models.*;
import com.insurance.application.models.dtos.InitialInfoStringDto;
import com.insurance.application.models.dtos.InitialPolicyDto;
import com.insurance.application.services.*;
import com.insurance.application.utils.ConvertDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


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

    @GetMapping("/new")
    public String newPolicy(Principal principal) {
        deletePolicy(principal);
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String policyProfile(Principal principal) {
        deletePolicy(principal);
        return "redirect:/profile";
    }

    @GetMapping("/user-filter")
    public String policyFilterUser(Principal principal) {
        deletePolicy(principal);
        return "redirect:/user-filter";
    }

    @GetMapping("/agent-filter")
    public String policyFilterAgent(Principal principal) {
        deletePolicy(principal);
        return "redirect:/agent-filter";
    }

    @GetMapping("/logout")
    public String policyLogout(Principal principal) {
        deletePolicy(principal);
        return "redirect:/logout";
    }

    @GetMapping
    public String getPolicy(
            Model model,
            Principal principal,
            HttpSession session
    ) {
        try {
            UserInfo user = userService.getByEmail(principal.getName());
            String tokenValue = userService.getByEmail(principal.getName()).getToken().getTokenValue();
            InitialInfoStringDto infoDto = infoDtoService.getByTokenValue(tokenValue);
            session.setAttribute("userToken", tokenValue);

            model.addAttribute("userInfo", user);
            model.addAttribute("infoDto", infoDto);

            UserInfo userInfo = userService.getByEmail(principal.getName());

            if(userInfo.getFirstname() != null) {
                InitialPolicyDto initialPolicyDto = new InitialPolicyDto();
                initialPolicyDto.setFirstName(userInfo.getFirstname());
                initialPolicyDto.setLastName(userInfo.getLastname());
                initialPolicyDto.setAddress(userInfo.getAddress());
                initialPolicyDto.setPhoneNumber(userInfo.getPhoneNumber());

                model.addAttribute("policyInfoDto", initialPolicyDto);
            } else {
                model.addAttribute("policyInfoDto", new InitialPolicyDto());
            }

            return "policy";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login";
        }

    }

    @PostMapping
    public String createPolicy(
            Principal principal,
            final InitialPolicyDto initialPolicyDto,
            HttpSession session
    ) {

        try {
            String tokenValue = (String) session.getAttribute("userToken");
            InitialInfoStringDto stringDto = infoDtoService.getByTokenValue(tokenValue);


            UserInfo user = configureUserDetails(principal, initialPolicyDto, stringDto);
            userService.update(user);

            Car car = enlistCar(stringDto, carBrandService, carModelService, user);
            carService.create(car);

            Policy policy = generatePolicy(initialPolicyDto, stringDto, user, car);
            policyService.create(policy);
            infoDtoService.delete(stringDto);
            return "redirect:/profile";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }

    }

    private Policy generatePolicy(InitialPolicyDto initialPolicyDto, InitialInfoStringDto stringDto, UserInfo user, Car car) throws ParseException {
        Policy policy = new Policy();
        policy.setApproval((byte) 0);
        policy.setCar(car);
        policy.setUserInfo(user);
        policy.setTotalPrice(stringDto.getTotalPrice());
        policy.setStartDate(initialPolicyDto.getStartDate());
        policy.setStartTime(initialPolicyDto.getStartTime());
        return policy;
    }

    private UserInfo configureUserDetails(Principal principal, InitialPolicyDto initialPolicyDto, InitialInfoStringDto stringDto) throws ParseException {
        UserInfo user = userService.getByEmail(principal.getName());
        user.setBirthdate(dateFormat(stringDto.getDriverBirthDate()));
        user.setFirstname(initialPolicyDto.getFirstName());
        user.setLastname(initialPolicyDto.getLastName());
        user.setPhoneNumber(initialPolicyDto.getPhoneNumber());
        user.setAddress(initialPolicyDto.getAddress());
        return user;
    }

    private Car enlistCar(InitialInfoStringDto stringDto, CarBrandService carBrandService, CarModelService carModelService, UserInfo user) throws ParseException {
        Car car = new Car();
        CarModel model = carModelService.getByModelName(stringDto.getCarModel());
        car.setUserInfo(user);
        car.setCarModel(model);
        car.setRegDate(dateFormat(stringDto.getRegistrationDate()));
        car.setCubicCap(stringDto.getCarCubic());

        return car;
    }

    private static LocalDate dateFormat(String date) throws ParseException {
        return LocalDate.parse(ConvertDate.convertDateForSQL(date));
    }

    private void deletePolicy(Principal principal) {
        String tokenValue = userService.getByEmail(principal.getName()).getToken().getTokenValue();
        InitialInfoStringDto infoDto = infoDtoService.getByTokenValue(tokenValue);

        infoDtoService.delete(infoDto);
    }
}