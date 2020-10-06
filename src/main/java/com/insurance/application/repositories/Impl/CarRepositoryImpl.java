package com.insurance.application.repositories.Impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CarRepositoryImpl {

    private final SessionFactory factory;

    @Autowired
    public CarRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }

}
