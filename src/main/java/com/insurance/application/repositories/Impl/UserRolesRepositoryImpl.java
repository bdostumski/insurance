package com.insurance.application.repositories.Impl;

import com.insurance.application.models.UserRole;
import com.insurance.application.repositories.UserRolesRepository;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

@Repository
public class UserRolesRepositoryImpl implements UserRolesRepository {

    SessionFactory sessionFactory;

    public UserRolesRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(UserRole role){
        try (Session session = sessionFactory.openSession()) {
            session.save(role);
        }
    }

    @Override
    public UserRole getByValue(String value){
        try (Session session = sessionFactory.openSession()) {
            Query<UserRole> query = session.createQuery(" from UserRole where authority = :authority", UserRole.class);
            query.setParameter("authority", value);
            return (UserRole) query.getSingleResult();
        }catch (NoResultException | NonUniqueResultException exception) {
            throw new EntityNotFoundException(" No user role found ");
        }
    }

}
