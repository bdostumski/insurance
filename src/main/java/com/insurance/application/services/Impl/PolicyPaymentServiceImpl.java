package com.insurance.application.services.Impl;

import com.insurance.application.services.PolicyPaymentService;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class PolicyPaymentServiceImpl implements PolicyPaymentService {

    @Override
    public double calculateNetPremium(boolean hasAccident, boolean isDriverUnderLimitAge,
                                      double netPremium, double accidentCoefficient, double driverAgeCoefficient) {

        if(hasAccident)
            netPremium = netPremium + (netPremium * accidentCoefficient);

        if(isDriverUnderLimitAge)
            netPremium = netPremium + (netPremium * driverAgeCoefficient);

        return netPremium;
    }

    @Override
    public double calculateTotalPremium(double netPremium, double taxAmount) {
        return roundUpFormat(netPremium + (taxAmount * netPremium));
    }

    private static double roundUpFormat(double baseAmount) {
        String sign = "#".repeat(2);
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(baseAmount));
    }
}
