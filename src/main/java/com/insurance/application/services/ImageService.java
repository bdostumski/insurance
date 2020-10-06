package com.insurance.application.services;

import com.insurance.application.models.Image;

import java.util.List;

public interface ImageService {

    Image getById(int id);

    List<Image> getAll();

    void create(Image image);

    void delete(int id);

    void update(int id);

}
