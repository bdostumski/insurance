package com.insurance.application.services;

import com.insurance.application.models.Policy;

import java.util.List;

public interface PolicyFilterService {

    List<Policy> filterForUser (int userId, String fromDate, String toDate);

    List<Policy> filterForAdmin (String fromDate, String toDate, String mail);

}
