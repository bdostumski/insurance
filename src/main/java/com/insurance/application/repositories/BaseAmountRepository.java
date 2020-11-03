package com.insurance.application.repositories;

import java.util.List;

public interface BaseAmountRepository {

    double getBaseAmount(int car_cubic, int car_age);

    List<Integer> getMaxCarCubics();

}
