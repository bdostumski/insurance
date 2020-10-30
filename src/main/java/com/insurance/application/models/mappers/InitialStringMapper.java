package com.insurance.application.models.mappers;

import com.insurance.application.models.dtos.InitialInfoDto;
import com.insurance.application.models.dtos.InitialInfoStringDto;
import com.insurance.application.services.BaseAmountService;
import com.insurance.application.services.CarBrandService;
import com.insurance.application.services.CarModelService;
import com.insurance.application.services.CoefficientService;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static com.insurance.application.utils.CalcUtil.calcAge;
import static com.insurance.application.utils.CalcUtil.getCarCubic;
import static com.insurance.application.utils.Constants.COEFFICIENT_INDEX;
import static com.insurance.application.utils.ConvertDate.dateFormat;

public class InitialStringMapper {

    public static InitialInfoStringDto initialStringMapper(InitialInfoStringDto infoStringDto,
            CarBrandService carBrandService,
            CarModelService carModelService,
            InitialInfoDto initialInfoDto,
            String tokenValue,
            double totalValue) {

        infoStringDto.setCarBrand(carBrandService.getById(initialInfoDto.getCarBrand()).getBrand());
        infoStringDto.setCarModel(carModelService.getById(initialInfoDto.getCarModel()).getModel());
        infoStringDto.setCarCubic(initialInfoDto.getCarCubic());
        infoStringDto.setUserToken(tokenValue);

        infoStringDto.setRegistrationDate(initialInfoDto.getRegistrationDate());
        infoStringDto.setDriverBirthDate(initialInfoDto.getDriverBirthDate());

        boolean isHasAccidents = initialInfoDto.getHasAccidents();

        String hasAccidents = isHasAccidents ? "Yes" : "No";
        infoStringDto.setHasAccidents(hasAccidents);

        infoStringDto.setTotalPrice(totalValue);

        return infoStringDto;
    }
}
