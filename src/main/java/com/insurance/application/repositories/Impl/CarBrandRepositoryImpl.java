package com.insurance.application.repositories.Impl;

import com.insurance.application.exceptions.EntityNotFoundException;
import com.insurance.application.models.CarBrand;
import com.insurance.application.repositories.CarBrandRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarBrandRepositoryImpl implements CarBrandRepository {

    private final SessionFactory factory;

    @Autowired
    public CarBrandRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public CarBrand getById(int id) {
        try (Session session = factory.openSession()) {
            CarBrand carBrand = session.get(CarBrand.class, id);
            if(carBrand == null) {
                throw new EntityNotFoundException(String.format("Car brand with ID %d not found", id));
            }
            return carBrand;
        }
    }

    @Override
    public List<CarBrand> getAll() {
        try(Session session = factory.openSession()) {
            Query<CarBrand> carBrandQuery = session.createQuery("from CarBrand");
            return carBrandQuery.list();
        }
    }
}
