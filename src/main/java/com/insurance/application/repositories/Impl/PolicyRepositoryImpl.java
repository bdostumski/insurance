package com.insurance.application.repositories.Impl;

import com.insurance.application.models.Policy;
import com.insurance.application.repositories.PolicyRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PolicyRepositoryImpl implements PolicyRepository {

    private final SessionFactory factory;

    @Autowired
    public PolicyRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }


    @Override
    public void create(Policy policy) {
        try (Session session = factory.openSession()) {
            session.save(policy);
        }
    }

    @Override
    public void update(Policy policy) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.update(policy);
            session.getTransaction().commit();
        }

    }

    @Override
    public void delete(Policy policy) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.delete(policy);
            session.getTransaction().commit();
        }
    }

    @Override
    public Policy getById(int id) {
        try (Session session = factory.openSession()) {
            Policy policy = session.get(Policy.class, id);
            return policy;
        }
    }

    @Override
    public List<Policy> getAllPolicies() {
        try (Session session = factory.openSession()) {
            Query<Policy> policyQuery = session.createQuery("from Policy");
            return policyQuery.list();
        }
    }

    @Override
    public List<Policy> getByUserMail(String userMail) {
        try (Session session = factory.openSession()) {
            Query<Policy> query = session.createQuery(" from Policy where userInfo.email = :userMail", Policy.class);
            query.setParameter("userMail", userMail);
            return query.list();
        }
    }

    @Override
    public List<Policy> getByUserId(int userId) {
        try (Session session = factory.openSession()) {
            Query<Policy> query = session.createQuery(" from Policy where userInfo.id = :userId", Policy.class);
            query.setParameter("userId", userId);
            return query.list();
        }
    }
}
