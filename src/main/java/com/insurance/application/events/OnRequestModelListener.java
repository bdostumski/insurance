package com.insurance.application.events;

import com.insurance.application.models.CarBrand;
import com.insurance.application.models.CarModel;
import com.insurance.application.services.CarBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import java.util.ArrayList;

public class OnRequestModelListener implements ApplicationListener<OnRequestModelsEvent> {

    @Autowired
    CarBrandService brandService;

    @Override
    public void onApplicationEvent(OnRequestModelsEvent event) {
        this.getModelsFromEvent(event);
    }

    private void getModelsFromEvent(OnRequestModelsEvent event) {
        CarBrand brand = brandService.getById(event.getBrandId());

        event.getSession().setAttribute("modelsList", new ArrayList<CarModel>(brand.getCarModelSet()));
    }
}
