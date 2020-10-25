package com.insurance.application.controllers.rest;


import com.insurance.application.models.Policy;
import com.insurance.application.models.dtos.PolicyRequestDto;
import com.insurance.application.services.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v.1.0/api/policy")
public class PolicyRestController {

    private final PolicyService policyService;

    @Autowired
    public PolicyRestController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping("/profile/{userId}")
    public List<PolicyRequestDto> getAllpoliciesOfUser(
            @PathVariable("userId") int userId
    ) {
        List<PolicyRequestDto> policyRequestList = new ArrayList<PolicyRequestDto>();
        List<Policy> policiesList = policyService.getByUserId(userId);
        for (Policy policy : policiesList){
            policyRequestList.add(createPolicyRequestDto(policy));
        }

        return policyRequestList;
    }

    private PolicyRequestDto createPolicyRequestDto (Policy policy){
        PolicyRequestDto policyRequestDto = new PolicyRequestDto();
        policyRequestDto.setPolicyId(policy.getId());
        policyRequestDto.setStartDate(policy.getStartDate());
        policyRequestDto.setStartTime(policy.getStartTime());
        policyRequestDto.setPolicyApprovalStatus(policy.getApproval());
        policyRequestDto.setVechicleBrand(policy.getCar().getCarModel().getCarBrand().getBrand());
        policyRequestDto.setVechicleModel(policy.getCar().getCarModel().getModel());
        policyRequestDto.setVehicleRegDate(policy.getCar().getConvertedRegDate());
        policyRequestDto.setPolicyPrice(policy.getTotalPrice());
        return policyRequestDto;
    }
}
