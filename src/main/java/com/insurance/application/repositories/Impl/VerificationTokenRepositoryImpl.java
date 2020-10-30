package com.insurance.application.repositories.Impl;

import com.insurance.application.models.Token;
import com.insurance.application.repositories.VerificationTokenRepository;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.insurance.application.exceptions.exceptionclasses.EntityNotFoundException;
import javax.persistence.NoResultException;

@Repository
public class VerificationTokenRepositoryImpl implements VerificationTokenRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public VerificationTokenRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Token token) {
        try (Session session = sessionFactory.openSession()) {
            session.save(token);
        }
    }

    @Override
    public Token findByToken(String token){
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(" from Token where tokenValue = :token", Token.class);
            query.setParameter("token", token);
            return (Token) query.getSingleResult();
        }catch (NoResultException | NonUniqueResultException exception) {
            throw new EntityNotFoundException(" No token found ");
        }
    }

    @Override
    public void delete(Token token) {
        try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.delete(token);
                session.getTransaction().commit();
        }catch (NoResultException | NonUniqueResultException exception) {
            throw new EntityNotFoundException(" No token found ");
        }
    }
}
