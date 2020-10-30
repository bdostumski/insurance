package com.insurance.application.services;

import com.insurance.application.models.Image;

import java.util.List;

public interface ImageService {

    Image getById(int id);

    void create(Image image);

    void delete(int id);

    void update(int id);

}
