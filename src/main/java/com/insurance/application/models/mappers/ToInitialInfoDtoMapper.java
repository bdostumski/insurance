package com.insurance.application.models.mappers;

import com.insurance.application.models.CarModel;
import com.insurance.application.models.UserInfo;
import com.insurance.application.models.dtos.InitialInfoDto;
import com.insurance.application.models.dtos.PolicyRequestDto;
import com.insurance.application.services.CarModelService;

import static com.insurance.application.utils.Constants.HAD_ACCIDENT_LAST_YEAR;

public class ToInitialInfoDtoMapper {

    public static InitialInfoDto initialInfoDto(PolicyRequestDto requestDto, UserInfo user, CarModelService carModelService) {
        InitialInfoDto infoDto = new InitialInfoDto();
        CarModel model = carModelService.getByModelName(requestDto.getVechicleModel());

        boolean prevAccidents = user.getPrevAccident() == HAD_ACCIDENT_LAST_YEAR;

        infoDto.setCarCubic(requestDto.getVehicleCubicCapacity());
        infoDto.setRegistrationDate(requestDto.getVehicleRegDate());
        infoDto.setHasAccidents(prevAccidents);
        infoDto.setDriverBirthDate(user.getBirthdate());
        infoDto.setCarBrand(model.getCarBrand().getId());
        infoDto.setCarModel(model.getId());

        return infoDto;
    }

}
