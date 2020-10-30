package com.insurance.application.services;

public interface PolicyPaymentService {

    double netPremium(boolean hasAccident, boolean isDriverUnderLimitAge,
                      double netPremium, double accidentCoefficient, double driverAgeCoefficient);

    double totalPremium(double netPremium, double taxAmount);

}
