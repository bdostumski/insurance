package com.insurance.application.repositories.Impl;

import com.insurance.application.exceptions.exceptionclasses.EntityNotFoundException;
import com.insurance.application.models.CarBrand;
import com.insurance.application.repositories.CarBrandRepository;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
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

    public CarBrand getByBrandName(String brandName){
        try (Session session = factory.openSession()) {
            Query<CarBrand> query = session.createQuery(" from CarBrand where brand = :brandName", CarBrand.class);
            query.setParameter("brandName", brandName);
            return (CarBrand) query.getSingleResult();
        }catch (NoResultException | NonUniqueResultException exception) {
            throw new EntityNotFoundException(" No such Car Brand found");
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
