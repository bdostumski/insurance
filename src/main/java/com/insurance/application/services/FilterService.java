package com.insurance.application.services;

import com.insurance.application.models.Policy;
import com.insurance.application.models.UserInfo;

import java.util.List;

public interface FilterService {

    List<Policy> filterForUser (int userId, String fromDate, String toDate);

    List<Policy> filterForAgent(String fromDate, String toDate, String mail);

    List<UserInfo> filterForAdmin(String firstname, String lastname, String mail);

}
