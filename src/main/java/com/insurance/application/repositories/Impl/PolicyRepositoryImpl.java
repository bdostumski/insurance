package com.insurance.application.repositories.Impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PolicyRepositoryImpl {

    private final SessionFactory factory;

    @Autowired
    public PolicyRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }

}
