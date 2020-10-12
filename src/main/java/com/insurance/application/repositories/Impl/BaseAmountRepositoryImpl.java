package com.insurance.application.repositories.Impl;

import com.insurance.application.exceptions.EntityNotFoundException;
import com.insurance.application.repositories.BaseAmountRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public class BaseAmountRepositoryImpl implements BaseAmountRepository {

    private final SessionFactory factory;

    public BaseAmountRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public double getBaseAmount(int car_cubic, int car_age) {
        try (Session session = factory.openSession()) {
            String sql = "select baseAmount from BaseAmount where ccMin <= :cc and :cc <= ccMax and carAgeMin <= :ca and :ca <= carAgeMax";
            Query<Double> query = session.createQuery(sql, Double.class);
            query.setParameter("cc", car_cubic);
            query.setParameter("ca", car_age);

            return query.list().stream().mapToDouble(d -> d).max().orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        }
    }
}
