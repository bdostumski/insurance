package com.insurance.application.models.mappers;

import com.insurance.application.models.dtos.InitialInfoDto;
import com.insurance.application.models.dtos.InitialInfoStringDto;
import com.insurance.application.services.BaseAmountService;
import com.insurance.application.services.CarBrandService;
import com.insurance.application.services.CarModelService;
import com.insurance.application.services.CoefficientService;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static com.insurance.application.utils.Constants.COEFFICIENT_INDEX;
import static com.insurance.application.utils.ConvertDate.convertDate;

public class InitialStringMapper {


    public static InitialInfoStringDto initialStringMapper(
            InitialInfoStringDto infoStringDto,
            CarBrandService carBrandService,
            CarModelService carModelService,
            BaseAmountService baseAmountService,
            CoefficientService coefficientService,
            InitialInfoDto initialInfoDto,
            String tokenValue
    ) throws ParseException {

        infoStringDto.setCarBrand(carBrandService.getById(initialInfoDto.getCarBrand()).getBrand());
        infoStringDto.setCarModel(carModelService.getById(initialInfoDto.getCarModel()).getModel());
        infoStringDto.setCarCubic(initialInfoDto.getCarCubic());
        infoStringDto.setTokenValue(tokenValue);

        String registrationDate = convertDate(initialInfoDto.getRegistrationDate());
        infoStringDto.setRegistrationDate(registrationDate);

        String DriverBirthDate = convertDate(initialInfoDto.getDriverBirthDate());
        infoStringDto.setDriverBirthDate(DriverBirthDate);

        boolean isHasAccidents = initialInfoDto.getHasAccidents();

        String hasAccidents = isHasAccidents ? "Yes" : "No";
        infoStringDto.setHasAccidents(hasAccidents);

        infoStringDto.setTotalPrice(calculateTotalPremium(
                initialInfoDto.getCarCubic(),
                initialInfoDto.getRegistrationDate(),
                initialInfoDto.getDriverBirthDate(),
                baseAmountService,
                coefficientService,
                isHasAccidents
                )
        );

        return infoStringDto;
    }

    public static double calculateTotalPremium(String carCubic,
                                               String registrationDate,
                                               String driverBirthdate,
                                               BaseAmountService baseAmountService,
                                               CoefficientService coefficientService,
                                               boolean isHasAccidents) {

        LocalDate currentDate = LocalDate.now();

        int carMaxCubic = getCarCubic(carCubic);
        int carAge = calcAge(dateFormat(registrationDate), currentDate);
        int driverAge = calcAge(dateFormat(driverBirthdate), currentDate);
        double netPremium = baseAmountService.getBaseAmount(carMaxCubic, carAge);

        double taxAmount = coefficientService.getById(COEFFICIENT_INDEX).getTaxAmount();
        double accidentCoefficient = coefficientService.getById(COEFFICIENT_INDEX).getAccident();
        double ageCoefficient = coefficientService.getById(COEFFICIENT_INDEX).getAgeCoefficient();
        int ageLimit = coefficientService.getById(COEFFICIENT_INDEX).getAgeLimit();

        if(isHasAccidents)
            netPremium = netPremium + (netPremium * accidentCoefficient);

        if(driverAge <= ageLimit)
            netPremium = netPremium + (netPremium * ageCoefficient);

        double totalPremium = netPremium + (taxAmount * netPremium);

        totalPremium = doubleFormatting(totalPremium);
        return totalPremium;
    }

    private static LocalDate dateFormat(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    private static int calcAge(LocalDate registrationDate, LocalDate currentDate) {
        return Period.between(registrationDate, currentDate).getYears();
    }

    private static int getCarCubic(String carCubic) {
        String[] carCubicString = carCubic.split(" ");
        return Integer.parseInt(carCubicString[2].replace(" ", ""));
    }

    private static double doubleFormatting(double baseAmount) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(baseAmount));
    }
}
