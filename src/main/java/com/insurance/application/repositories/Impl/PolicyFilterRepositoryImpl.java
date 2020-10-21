package com.insurance.application.repositories.Impl;

import com.insurance.application.models.Policy;
import com.insurance.application.repositories.PolicyFilterRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PolicyFilterRepositoryImpl implements PolicyFilterRepository {

    private final SessionFactory factory;

    @Autowired
    public PolicyFilterRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }


    @Override
    public List<Policy> filterForUser(int userId, String fromDate, String toDate) {

        try(Session session = factory.openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Policy> cr = cb.createQuery(Policy.class);
            Root<Policy> root = cr.from(Policy.class);
            List<Predicate> predicates = new ArrayList<>();

            if(fromDate.isBlank() || toDate.isBlank()) {

                predicates.add(cb.equal(root.get("userInfo").get("id"), userId));
                cr.select(root).where(predicates.toArray(Predicate[]::new));
                Query<Policy> query = session.createQuery(cr);
                return query.getResultList();

            } else {

                predicates.add(cb.between(root.get("startDate"), fromDate, toDate));
                predicates.add(cb.equal(root.get("userInfo").get("id"), userId));
                cr.select(root).where(predicates.toArray(Predicate[]::new));
                Query<Policy> query = session.createQuery(cr);
                return query.getResultList();

            }
        }
    }

    @Override
    public List<Policy> filterForAdmin(String fromDate, String toDate, String mail) {
        try(Session session = factory.openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Policy> cr = cb.createQuery(Policy.class);
            Root<Policy> root = cr.from(Policy.class);
            List<Predicate> predicates = new ArrayList<>();

            if(fromDate.isBlank() || toDate.isBlank() || mail.isBlank()) {

                cr.select(root).where(predicates.toArray(Predicate[]::new));
                Query<Policy> query = session.createQuery(cr);
                return query.getResultList();

            } else {

                predicates.add(cb.between(root.get("startDate"), fromDate, toDate));
                predicates.add(cb.like(root.get("userInfo").get("email"), "%" + mail + "%"));
                cr.select(root).where(predicates.toArray(Predicate[]::new));
                Query<Policy> query = session.createQuery(cr);
                return query.getResultList();

            }
        }
    }
}
