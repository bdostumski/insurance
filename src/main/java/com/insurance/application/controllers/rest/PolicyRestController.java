package com.insurance.application.controllers.rest;


import com.insurance.application.models.Policy;
import com.insurance.application.models.dtos.PolicyRequestDto;
import com.insurance.application.security.jwt.JwtTokenUtil;
import com.insurance.application.services.PolicyService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static com.insurance.application.utils.Constants.APPROVAL_STATUS_WITHDRAWN;

@RestController
@RequestMapping("/v.1.0/api/policy")
public class PolicyRestController {

    private final PolicyService policyService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public PolicyRestController(PolicyService policyService, JwtTokenUtil jwtTokenUtil) {
        this.policyService = policyService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @GetMapping("/profile/{userId}")
    public List<PolicyRequestDto> getAllpoliciesOfUser(@PathVariable("userId") int userId) {

        List<PolicyRequestDto> policyRequestList = new ArrayList<PolicyRequestDto>();
        List<Policy> policiesList = policyService.getByUserId(userId);
        for (Policy policy : policiesList) {
            policyRequestList.add(createPolicyRequestDto(policy));
        }

        return policyRequestList;
    }

    @PutMapping("/withdrawn/{policy-id}")
    public void withdrawUserPendingPolicy( HttpServletRequest request, @PathVariable ("policy-id") int policyId){
        String userMail = getUserEmail(request, jwtTokenUtil);
        Policy policy = policyService.getById(policyId);
        if (policy.getUserInfo().getEmail().equals(userMail)){
            policy.setApproval(APPROVAL_STATUS_WITHDRAWN);
            policyService.update(policy);
        }else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
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


    private String getUserEmail(HttpServletRequest request, JwtTokenUtil jwtTokenUtil) {
        final String requestTokenHeader = request.getHeader("Authorization");

        String email = null;
        String jwtToken;
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")
        ) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                email = jwtTokenUtil.getEmailFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        }
        return email;
    }
}
