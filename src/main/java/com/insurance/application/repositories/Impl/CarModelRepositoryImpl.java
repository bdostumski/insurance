package com.insurance.application.repositories.Impl;

import com.insurance.application.exceptions.exceptionclasses.EntityNotFoundException;
import com.insurance.application.models.CarModel;
import com.insurance.application.repositories.CarModelRepository;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class CarModelRepositoryImpl implements CarModelRepository {

    private final SessionFactory factory;

    @Autowired
    public CarModelRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public CarModel getById(int id) {
        try (Session session = factory.openSession()) {
            CarModel carModel = session.get(CarModel.class, id);
            if(carModel == null) {
                throw new EntityNotFoundException(String.format("Car model with ID %d not found", id));
            }
            return carModel;
        }
    }

    public CarModel getByModelName(String modelName){
        try (Session session = factory.openSession()) {
            Query<CarModel> query = session.createQuery(" from CarModel where model = :modelName", CarModel.class);
            query.setParameter("modelName", modelName);
            return (CarModel) query.getSingleResult();
        }catch (NoResultException | NonUniqueResultException exception) {
            throw new EntityNotFoundException(" No such Car Model found");
        }
    }

    @Override
    public List<CarModel> getAll() {
        try (Session session = factory.openSession()) {
            Query<CarModel> carModelQuery = session.createQuery("from CarModel");
            return carModelQuery.list();
        }
    }
}
