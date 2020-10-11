package com.insurance.application.repositories.Impl;

import com.insurance.application.models.Coefficient;
import com.insurance.application.repositories.CoefficientRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CoefficientRepositoryImpl implements CoefficientRepository {

    private final SessionFactory factory;

    @Autowired
    public CoefficientRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Coefficient getById(int id) {
        try (Session session = factory.openSession()) {
            Coefficient coefficient = session.get(Coefficient.class, id);
            return coefficient;
        }
    }
}
