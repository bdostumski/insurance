package com.insurance.application.controllers.rest;


import com.insurance.application.exceptions.exceptionclasses.EntityNotFoundException;
import com.insurance.application.models.*;
import com.insurance.application.models.dtos.InitialInfoDto;
import com.insurance.application.models.dtos.PolicyRequestDto;
import com.insurance.application.models.mappers.CarMapper;
import com.insurance.application.models.mappers.ToInitialInfoDtoMapper;
import com.insurance.application.security.jwt.JwtTokenUtil;
import com.insurance.application.services.*;
import com.insurance.application.utils.CalcUtil;
import com.insurance.application.utils.ConvertDate;
import com.insurance.application.utils.Validator;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static com.insurance.application.utils.Constants.*;

@RestController
@RequestMapping("/v.1.0/api/policy")
public class PolicyRestController {

    private final PolicyService policyService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserInfoService userInfoService;
    private final CarModelService carModelService;
    private final CarService carService;
    private final ImageService imageService;

    @Autowired
    public PolicyRestController(PolicyService policyService, JwtTokenUtil jwtTokenUtil,
                                UserInfoService userInfoService, CarModelService carModelService,
                                CarService carService, ImageService imageService) {
        this.policyService = policyService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userInfoService = userInfoService;
        this.carModelService = carModelService;
        this.carService = carService;
        this.imageService = imageService;
    }

    @GetMapping("/profile/{userId}")
    @ApiOperation(value = "Get all policies of user", response = List.class)
    public List<PolicyRequestDto> getAllPoliciesOfUser(@PathVariable("userId") int userId) {

        List<PolicyRequestDto> policyRequestList = new ArrayList<PolicyRequestDto>();
        List<Policy> policiesList = policyService.getByUserId(userId);
        for (Policy policy : policiesList) {
            policyRequestList.add(createPolicyRequestDto(policy));
        }

        return policyRequestList;
    }

    @PutMapping("/withdrawn/{policy-id}")
    @ApiOperation(value = "Withdraw user pending policy")
    public void withdrawUserPendingPolicy(HttpServletRequest request, @PathVariable("policy-id") int policyId) {
        String userMail = Validator.getUserEmail(request, jwtTokenUtil);
        Policy policy = policyService.getById(policyId);
        if (policy.getUserInfo().getEmail().equals(userMail)) {
            policy.setApproval(APPROVAL_STATUS_WITHDRAWN);
            policyService.update(policy);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping
    @ApiOperation(value = "Create new policy", response = Car.class)
    public ResponseEntity<PolicyRequestDto> createNewPolicy(HttpServletRequest request,
                                                            @RequestBody PolicyRequestDto policyRequestDto
    ) {

        try {
            String email = Validator.getUserEmail(request, jwtTokenUtil);
            UserInfo userInfo = userInfoService.getByEmail(email);

            InitialInfoDto initialInfoDto = ToInitialInfoDtoMapper.initialInfoDto(policyRequestDto, userInfo, carModelService);
            Car car = CarMapper.enlistCar(initialInfoDto, carModelService, carService, userInfo);
            Image image = imageService.getById(DEFAULT_IMAGE);

            Policy policy = createNewPolicy(policyRequestDto, userInfo, car, image);
            policyService.create(policy);
            image.setPolicy(policy);
            imageService.create(image);

            return new ResponseEntity<PolicyRequestDto>(HttpStatus.OK);

        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account has been disabled");
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong UserName or Password");
        }
    }


    private Policy createNewPolicy(PolicyRequestDto infoDto, UserInfo user, Car car, Image image) {
        Policy policy = new Policy();

        policy.setApproval(APPROVAL_STATUS_PENDING);
        policy.setStartTime(infoDto.getStartTime());
        policy.setStartDate(infoDto.getStartDate());
        policy.setTotalPrice(infoDto.getPolicyPrice());
        policy.setUserInfo(user);
        policy.setCar(car);
        policy.setImage(image);

        return policy;
    }


    private PolicyRequestDto createPolicyRequestDto(Policy policy) {
        PolicyRequestDto policyRequestDto = new PolicyRequestDto();
        policyRequestDto.setPolicyId(policy.getId());
        policyRequestDto.setStartDate(policy.getStartDate());
        policyRequestDto.setStartTime(policy.getStartTime());
        policyRequestDto.setPolicyApprovalStatus(policy.getApproval());
        policyRequestDto.setVechicleBrand(policy.getCar().getCarModel().getCarBrand().getBrand());
        policyRequestDto.setVechicleModel(policy.getCar().getCarModel().getModel());
        policyRequestDto.setVehicleRegDate(policy.getCar().getConvertedRegDate());
        policyRequestDto.setPolicyPrice(policy.getTotalPrice());
        policyRequestDto.setVehicleCubicCapacity(policy.getCar().getCubicCap());
        return policyRequestDto;
    }
}
