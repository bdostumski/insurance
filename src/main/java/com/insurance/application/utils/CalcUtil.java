package com.insurance.application.utils;

import com.insurance.application.models.dtos.InitialInfoDto;
import com.insurance.application.services.BaseAmountService;
import com.insurance.application.services.CoefficientService;
import com.insurance.application.services.PolicyPaymentService;

import java.time.LocalDate;
import java.time.Period;

import static com.insurance.application.utils.Constants.COEFFICIENT_INDEX;
import static com.insurance.application.utils.ConvertDate.dateFormat;

public class CalcUtil {

    private final static LocalDate currentDate = LocalDate.now();


    public static int calcAge(LocalDate registrationDate) {
        return Period.between(registrationDate, currentDate).getYears();
    }

    public static int getCarCubic(String carCubic) {
        String[] carCubicString = carCubic.split(" ");
        return Integer.parseInt(carCubicString[2].replace(" ", ""));
    }

    public static boolean isDriverUnderAge(String driverBirthdate,
                                           CoefficientService coefficientService) {

        int ageLimit = coefficientService.getById(COEFFICIENT_INDEX).getAgeLimit();
        int driverAge = calcAge(dateFormat(driverBirthdate));
        return  (driverAge <= ageLimit);
    }

    public static double calculateTotalPremium(InitialInfoDto dto, CoefficientService coefficientService, BaseAmountService baseAmountService,
                                               PolicyPaymentService policyPaymentService) {

        boolean hasAccident = dto.getHasAccidents();
        boolean isDriverUnderLimitAge = isDriverUnderAge(dto.getDriverBirthDate(), coefficientService);

        int carAge = calcAge(dateFormat(dto.getRegistrationDate()));
        int carCubic = getCarCubic(dto.getCarCubic());

        double taxAmount = coefficientService.getById(1).getTaxAmount();
        double baseAmount = baseAmountService.getBaseAmount(carCubic, carAge);
        double accidentCoefficient = coefficientService.getById(1).getAccident();
        double driverAgeCoefficient =  coefficientService.getById(1).getAgeCoefficient();
        double netPremium = policyPaymentService.calculateNetPremium(hasAccident, isDriverUnderLimitAge,
                baseAmount, accidentCoefficient, driverAgeCoefficient);

        double totalPremium = policyPaymentService.calculateTotalPremium(netPremium, taxAmount);
        return totalPremium;
    }
}
