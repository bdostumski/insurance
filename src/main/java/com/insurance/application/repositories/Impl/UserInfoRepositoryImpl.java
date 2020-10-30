package com.insurance.application.repositories.Impl;

import com.insurance.application.models.UserInfo;
import com.insurance.application.repositories.UserInfoRepository;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insurance.application.exceptions.exceptionclasses.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class UserInfoRepositoryImpl implements UserInfoRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserInfoRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<UserInfo> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<UserInfo> userInfoQuery = session.createQuery("from UserInfo", UserInfo.class);
            return userInfoQuery.list();
        }
    }

    @Override
    public void create(UserInfo user) {
        try (Session session = sessionFactory.openSession()) {
            session.save(user);
        }
    }

    @Override
    public void update(UserInfo user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(UserInfo user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public UserInfo getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            UserInfo user = session.get(UserInfo.class, id);
            return user;
        }
    }

    @Override
    public UserInfo getByLastName(String userName) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(" from UserInfo where lastname = :userName", UserInfo.class);
            query.setParameter("userName", userName);
            return (UserInfo) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException exception) {
            throw new EntityNotFoundException(" No user found ");
        }
    }

    @Override
    public UserInfo getByEmail(String userEmail) {
        UserInfo user;
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(" from UserInfo where email = :usermail", UserInfo.class);
            query.setParameter("usermail", userEmail);
            user = (UserInfo) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException exception) {
            throw new EntityNotFoundException(" No user found ");
        }
        return user;
    }

}
