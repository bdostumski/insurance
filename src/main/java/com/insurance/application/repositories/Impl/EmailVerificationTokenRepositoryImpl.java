package com.insurance.application.repositories.Impl;

import com.insurance.application.models.EmailVerificationToken;
import com.insurance.application.repositories.EmailVerificationTokenRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;

@Repository
public class EmailVerificationTokenRepositoryImpl implements EmailVerificationTokenRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public EmailVerificationTokenRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(EmailVerificationToken token) {
        try (Session session = sessionFactory.openSession()) {
            session.save(token);
        }
    }

    @Override
    public EmailVerificationToken findByToken(String token){
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(" from EmailVerificationToken where token = :token", EmailVerificationToken.class);
            query.setParameter("token", token);
            return (EmailVerificationToken) query.getSingleResult();

        }
    }

    @Override
    public void delete(String token) {
        try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.delete(token);
                session.getTransaction().commit();

        }catch (EntityNotFoundException e){
            e.printStackTrace();
        }
    }
}
