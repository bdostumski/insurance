package com.insurance.application.repositories.Impl;

import com.insurance.application.exceptions.EntityNotFoundException;
import com.insurance.application.models.UserInfo;
import com.insurance.application.repositories.UserInfoRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoRepositoryImpl implements UserInfoRepository {

    private static final boolean ACCOUNNT_STATUS_NOT_ACTIVE = false;

    private final SessionFactory sessionFactory;

    @Autowired
    public UserInfoRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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
            ;
            session.getTransaction().commit();
        }

    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            UserInfo user = getById(id);
            user.setEnabled(ACCOUNNT_STATUS_NOT_ACTIVE);
            session.update(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public UserInfo getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            UserInfo user = session.get(UserInfo.class, id);
            if (user == null || user.isEnabled() == ACCOUNNT_STATUS_NOT_ACTIVE) {
                throw new EntityNotFoundException(
                        String.format("User with id %d not found!", id));
            }
            return user;
        }
    }

    @Override
    public UserInfo getByLastName(String userName){
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(" from UserInfo where lastname = :userName", UserInfo.class);
            query.setParameter("userName", userName);
            return (UserInfo) query.getSingleResult();

        }
    }

    @Override
    public UserInfo getByEmail(String userEmail){
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(" from UserInfo where email = :usermail", UserInfo.class);
            query.setParameter("usermail", userEmail);
            return (UserInfo) query.getSingleResult();

        }
    }

}
