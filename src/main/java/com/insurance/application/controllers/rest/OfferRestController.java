package com.insurance.application.controllers.rest;

import com.insurance.application.exceptions.ErrorInformation;
import com.insurance.application.exceptions.exceptionclasses.EntityNotFoundException;
import com.insurance.application.models.CarModel;
import com.insurance.application.models.UserInfo;
import com.insurance.application.models.dtos.InitialInfoDto;
import com.insurance.application.models.dtos.PolicyRequestDto;
import com.insurance.application.models.mappers.ToInitialInfoDtoMapper;
import com.insurance.application.security.jwt.JwtTokenUtil;
import com.insurance.application.services.*;
import com.insurance.application.utils.CalcUtil;
import com.insurance.application.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/v.1.0/api/offer")
public class OfferRestController {

    private final BaseAmountService baseAmountService;
    private final CoefficientService coefficientService;
    private final PolicyPaymentService paymentService;
    private final UserInfoService userInfoService;
    private final CarModelService carModelService;
    private final JwtTokenUtil jwtTokenUtil;

    public OfferRestController(BaseAmountService baseAmountService, CoefficientService coefficientService,
                               PolicyPaymentService paymentService, UserInfoService userInfoService,
                               CarModelService carModelService, JwtTokenUtil jwtTokenUtil) {
        this.baseAmountService = baseAmountService;
        this.coefficientService = coefficientService;
        this.paymentService = paymentService;
        this.userInfoService = userInfoService;
        this.carModelService = carModelService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping
    public ResponseEntity<Double> requestOffer(HttpServletRequest request,
                                               @RequestBody PolicyRequestDto policyRequestDto
    ) {
        try {
            String email = Validator.getUserEmail(request, jwtTokenUtil);
            UserInfo userInfo = userInfoService.getByEmail(email);
            InitialInfoDto initialInfoDto = ToInitialInfoDtoMapper.initialInfoDto(policyRequestDto, userInfo, carModelService);
            double totalPrice = CalcUtil.calculateTtotalPremium(initialInfoDto, coefficientService, baseAmountService, paymentService);

            policyRequestDto.setPolicyPrice(totalPrice);

            return new ResponseEntity<Double>(totalPrice, HttpStatus.OK);
        }catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Additional info required");
        }
    }

    @GetMapping("/maxcubics")
    public List<Integer> getMaxCarCubicsValues() {
        return baseAmountService.getMaxCarCubics();
    }
}
