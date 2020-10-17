package com.insurance.application.repositories.Impl;

import com.insurance.application.models.Car;
import com.insurance.application.repositories.CarRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CarRepositoryImpl implements CarRepository {

    private final SessionFactory factory;

    @Autowired
    public CarRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }


    public void create(Car car) {
        try (Session session = factory.openSession()) {
            session.save(car);
        }
    }


    public void update(Car car) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.update(car);
            session.getTransaction().commit();
        }
    }


    public void delete(Car car) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.delete(car);
            session.getTransaction().commit();
        }
    }

    public Car getById(int id) {
        try (Session session = factory.openSession()) {
            Car car = session.get(Car.class, id);
            return car;
        }
    }

}
