package com.insurance.application.services;

public interface PolicyPaymentService {

    double calculateNetPremium(boolean hasAccident, boolean isDriverUnderLimitAge,
                               double netPremium, double accidentCoefficient, double driverAgeCoefficient);

    double calculateTotalPremium(double netPremium, double taxAmount);

}
