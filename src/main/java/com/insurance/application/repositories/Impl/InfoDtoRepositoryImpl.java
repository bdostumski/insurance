package com.insurance.application.repositories.Impl;

import com.insurance.application.exceptions.exceptionclasses.EntityNotFoundException;
import com.insurance.application.models.dtos.InitialInfoStringDto;
import com.insurance.application.repositories.InfoDtoRepository;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.persistence.NoResultException;

@Repository
public class InfoDtoRepositoryImpl implements InfoDtoRepository {

    private final SessionFactory factory;

    @Autowired
    public InfoDtoRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }


    @Override
    public void create(InitialInfoStringDto stringDto) {
        try (Session session = factory.openSession()) {
            session.save(stringDto);
        }
    }


    @Override
    public void update(InitialInfoStringDto stringDto) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.update(stringDto);
            session.getTransaction().commit();
        }
    }


    @Override
    public void delete(InitialInfoStringDto stringDto) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.delete(stringDto);
            session.getTransaction().commit();
        }
    }

    @Override
    public InitialInfoStringDto getById(int id) {
        try (Session session = factory.openSession()) {
            InitialInfoStringDto stringDto = session.get(InitialInfoStringDto.class, id);
            return stringDto;
        }
    }

    @Override
    public InitialInfoStringDto getByTokenValue(String tokenValue){
        try (Session session = factory.openSession()) {
            Query<InitialInfoStringDto> query = session.createQuery(" from InitialInfoStringDto where userToken = :token", InitialInfoStringDto.class);
            query.setParameter("token", tokenValue);
            return (InitialInfoStringDto) query.getSingleResult();
        }catch (NoResultException | NonUniqueResultException exception) {
            throw new EntityNotFoundException(" No InfoDto found for user");
        }
    }
}
