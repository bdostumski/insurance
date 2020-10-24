package com.insurance.application.repositories.Impl;

import com.insurance.application.models.Policy;
import com.insurance.application.models.UserInfo;
import com.insurance.application.repositories.FilterRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilterRepositoryImpl implements FilterRepository {

    private final SessionFactory factory;

    @Autowired
    public FilterRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Policy> filterForUser(int userId, String fromDate, String toDate) {
        try(Session session = factory.openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Policy> cr = cb.createQuery(Policy.class);
            Root<Policy> root = cr.from(Policy.class);
            List<Predicate> predicates = new ArrayList<>();

            if(!fromDate.isBlank())
                predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), fromDate));

            if(!toDate.isBlank())
                predicates.add(cb.lessThanOrEqualTo(root.get("startDate"), toDate));

            predicates.add(cb.equal(root.get("userInfo").get("id"), userId));

            if(fromDate.isBlank() && toDate.isBlank()) {
                cr.select(root).where(predicates.toArray(Predicate[]::new));
                Query<Policy> query = session.createQuery(cr);
                return query.getResultList();
            }

            cr.select(root).where(predicates.toArray(Predicate[]::new));
            Query<Policy> query = session.createQuery(cr);
            return query.getResultList();
        }
    }

    @Override
    public List<Policy> filterForAgent(String fromDate, String toDate, String mail) {
        try(Session session = factory.openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Policy> cr = cb.createQuery(Policy.class);
            Root<Policy> root = cr.from(Policy.class);
            List<Predicate> predicates = new ArrayList<>();

            if(!fromDate.isBlank())
                predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), fromDate));

            if(!toDate.isBlank())
                predicates.add(cb.lessThanOrEqualTo(root.get("startDate"), toDate));

            if(!mail.isBlank())
                predicates.add(cb.like(root.get("userInfo").get("email"), "%" + mail + "%"));

            if(fromDate.isBlank() && toDate.isBlank() && mail.isBlank()) {
                cr.select(root).where(predicates.toArray(Predicate[]::new));
                Query<Policy> query = session.createQuery(cr);
                return query.getResultList();
            }

            cr.select(root).where(predicates.toArray(Predicate[]::new));
            Query<Policy> query = session.createQuery(cr);
            return query.getResultList();
        }
    }

    @Override
    public List<UserInfo> filterForAdmin(String firstname,
                                         String lastname,
                                         String mail) {
        try(Session session = factory.openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<UserInfo> cr = cb.createQuery(UserInfo.class);
            Root<UserInfo> root = cr.from(UserInfo.class);
            List<Predicate> predicates = new ArrayList<>();

            if(!firstname.isBlank())
                predicates.add(cb.like(root.get("firstname"), "%" + firstname + "%"));

            if(!lastname.isBlank())
                predicates.add(cb.like(root.get("lastname"), "%" + lastname + "%"));

            if(!mail.isBlank())
                predicates.add(cb.like(root.get("email"), "%" + mail + "%"));

            if(firstname.isBlank() && lastname.isBlank() && mail.isBlank()) {
                cr.select(root).where(predicates.toArray(Predicate[]::new));
                Query<UserInfo> query = session.createQuery(cr);
                return query.getResultList();
            }

            cr.select(root).where(predicates.toArray(Predicate[]::new));
            Query<UserInfo> query = session.createQuery(cr);
            return query.getResultList();
        }
    }
}
