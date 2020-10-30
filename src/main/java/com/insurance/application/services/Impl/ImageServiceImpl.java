package com.insurance.application.services.Impl;

import com.insurance.application.models.Image;
import com.insurance.application.repositories.ImageRepository;
import com.insurance.application.services.ImageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    ImageRepository repository;

    public ImageServiceImpl(ImageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Image getById(int id) {
        return repository.getById(id);
    }

    @Override
    public void create(Image image) {
        repository.create(image);
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Override
    public void update(int id) {
        repository.update(id);
    }
}
