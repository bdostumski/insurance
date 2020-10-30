package com.insurance.application.repositories.Impl;

import com.insurance.application.exceptions.exceptionclasses.EntityNotFoundException;
import com.insurance.application.models.Image;
import com.insurance.application.repositories.ImageRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImageRepositoryImpl implements ImageRepository {

    private final SessionFactory factory;

    @Autowired
    public ImageRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Image getById(int id) {
        try (Session session = factory.openSession()) {
            Image image = session.get(Image.class, id);
            if(image == null) {
                throw new EntityNotFoundException(String.format("Image with ID %d not found", id));
            }
            return image;
        }
    }

    @Override
    public void create(Image image) {
        try (Session session = factory.openSession()) {
            session.save(image);
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Image imageToDelete = getById(id);
            session.delete(imageToDelete);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(int id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Image imageToUpdate = getById(id);
            session.update(imageToUpdate);
            session.getTransaction().commit();
        }
    }
}
