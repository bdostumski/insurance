package com.insurance.application.services;

import java.util.List;

public interface BaseAmountService {

    double getBaseAmount(int car_cubic, int car_age);

    List<Integer> getMaxCarCubics();

}
