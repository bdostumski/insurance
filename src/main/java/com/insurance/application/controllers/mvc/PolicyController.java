package com.insurance.application.controllers.mvc;

import com.insurance.application.imageprocessing.FileSystemStorageService;
import com.insurance.application.imageprocessing.FileUploadController;
import com.insurance.application.imageprocessing.StorageService;
import com.insurance.application.models.*;
import com.insurance.application.models.dtos.InitialInfoStringDto;
import com.insurance.application.models.dtos.InitialPolicyDto;
import com.insurance.application.models.mappers.CarMapper;
import com.insurance.application.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.security.Principal;

import static com.insurance.application.utils.Constants.*;


@Controller
@RequestMapping("/policy")
public class PolicyController {

    InfoDtoService infoDtoService;
    UserInfoService userService;
    PolicyService policyService;
    CarService carService;
    CarModelService carModelService;
    ImageService imageService;
    StorageService storageService;

    @Autowired
    public PolicyController(InfoDtoService infoDtoService, UserInfoService userService, PolicyService policyService,
                            CarService carService, CarModelService carModelService,
                            ImageService imageService, StorageService storageService) {
        this.infoDtoService = infoDtoService;
        this.userService = userService;
        this.policyService = policyService;
        this.carService = carService;
        this.carModelService = carModelService;
        this.imageService = imageService;
        this.storageService = storageService;
    }

    @GetMapping("/new")
    public String newPolicy(Principal principal, HttpSession session) {
        cleanCachedInfo(principal, session);
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String policyProfile(Principal principal, HttpSession session) {
        if (userService.getByEmail(principal.getName()).getFirstname() != null) {
            cleanCachedInfo(principal, session);
            return "redirect:/profile";
        }
        return "redirect:/policy";
    }

    @GetMapping("/user-filter")
    public String policyFilterUser(Principal principal, HttpSession session) {
        cleanCachedInfo(principal, session);
        return "redirect:/user-filter";
    }

    @GetMapping("/agent-filter")
    public String policyFilterAgent(Principal principal, HttpSession session) {
        cleanCachedInfo(principal, session);
        return "redirect:/agent-filter";
    }

    @GetMapping("/logout")
    public String policyLogout(Principal principal, HttpSession session) {
        cleanCachedInfo(principal, session);
        return "redirect:/logout";
    }

    @GetMapping
    public String getPolicy(Model model, Principal principal, HttpSession session) {

        UserInfo user = userService.getByEmail(principal.getName());
        model.addAttribute("userInfo", user);

        String tokenValue = userService.getByEmail(principal.getName()).getToken().getTokenValue();
        session.setAttribute("userToken", tokenValue);

        InitialInfoStringDto infoDto = infoDtoService.getByTokenValue(tokenValue);
        model.addAttribute("infoDto", infoDto);

        return loadUserDetails(user, model);

    }

    @PostMapping
    public String createPolicy(Principal principal,
                               final InitialPolicyDto initialPolicyDto,
                               @RequestParam("file") MultipartFile file,
                               HttpSession session) {

        String tokenValue = (String) session.getAttribute("userToken");
        InitialInfoStringDto stringDto = infoDtoService.getByTokenValue(tokenValue);

        UserInfo user = configureUserDetails(principal, initialPolicyDto, stringDto);

        Car car = CarMapper.enlistCar(stringDto, carModelService, carService, user);

        Policy policy = generatePolicy(initialPolicyDto, stringDto, user, car);
        policyService.create(policy);

        handleFileUpload(policy, file, user.getId());

        cleanCachedInfo(principal, session);
        session.removeAttribute("userToken");

        return "redirect:/profile";
    }

    private String loadUserDetails(UserInfo user, Model model) {
        if (user.getFirstname() != null) {
            InitialPolicyDto initialPolicyDto = new InitialPolicyDto();
            initialPolicyDto.setFirstName(user.getFirstname());
            initialPolicyDto.setLastName(user.getLastname());
            initialPolicyDto.setAddress(user.getAddress());
            initialPolicyDto.setPhoneNumber(user.getPhoneNumber());

            model.addAttribute("policyInfoDto", initialPolicyDto);
        } else {
            model.addAttribute("policyInfoDto", new InitialPolicyDto());
        }
        return "policy";
    }


    private Policy generatePolicy(InitialPolicyDto initialPolicyDto,
                                  InitialInfoStringDto stringDto,
                                  UserInfo user, Car car) {

        Policy policy = new Policy();
        policy.setApproval(APPROVAL_STATUS_PENDING);
        policy.setCar(car);
        policy.setUserInfo(user);
        policy.setTotalPrice(stringDto.getTotalPrice());
        policy.setStartDate(initialPolicyDto.getStartDate());
        policy.setStartTime(initialPolicyDto.getStartTime());

        return policy;
    }

    private UserInfo configureUserDetails(Principal principal,
                                          InitialPolicyDto initialPolicyDto,
                                          InitialInfoStringDto stringDto) {

        UserInfo user = userService.getByEmail(principal.getName());
        user.setBirthdate(stringDto.getDriverBirthDate());
        user.setFirstname(initialPolicyDto.getFirstName());
        user.setLastname(initialPolicyDto.getLastName());
        user.setPhoneNumber(initialPolicyDto.getPhoneNumber());
        user.setAddress(initialPolicyDto.getAddress());

        int accidents = stringDto.getHasAccidents().equals("No") ? NO_ACCIDENT_LAST_YEAR : HAD_ACCIDENT_LAST_YEAR;
        user.setPrevAccident((byte) accidents);
        userService.update(user);

        return user;
    }

    private void cleanCachedInfo(Principal principal, HttpSession session) {
        String tokenValue = userService.getByEmail(principal.getName()).getToken().getTokenValue();
        InitialInfoStringDto infoDto = infoDtoService.getByTokenValue(tokenValue);
        infoDtoService.delete(infoDto);
        session.removeAttribute("theToken");

    }

    private void handleFileUpload(Policy policy, MultipartFile file, int userId) {

        Image picture = new Image();
        if (file != null) {
            picture.setName(file.getOriginalFilename());
            picture.setPolicy(policy);
            picture.setPath(
                    MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                            "serveFile", file.getOriginalFilename()).build().toUri().toString()

            );
            imageService.create(picture);
            storageService.store(file);
        } else {
            picture = imageService.getById(30);
        }
    }
}