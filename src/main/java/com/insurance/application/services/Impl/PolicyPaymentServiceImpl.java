package com.insurance.application.services.Impl;

import com.insurance.application.services.PolicyPaymentService;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class PolicyPaymentServiceImpl implements PolicyPaymentService {

    @Override
    public double netPremium(boolean hasAccident, boolean isDriverUnderLimitAge,
                             double netPremium, double accidentCoefficient, double driverAgeCoefficient) {

        if(hasAccident)
            netPremium = netPremium + (netPremium * accidentCoefficient);

        if(isDriverUnderLimitAge)
            netPremium = netPremium + (netPremium * driverAgeCoefficient);

        return netPremium;
    }

    @Override
    public double totalPremium(double netPremium, double taxAmount) {
        return doubleFormatting(netPremium + (taxAmount * netPremium));
    }

    private static double doubleFormatting(double baseAmount) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(baseAmount));
    }
}
