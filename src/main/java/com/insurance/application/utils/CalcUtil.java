package com.insurance.application.utils;

import com.insurance.application.services.CoefficientService;

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
}
