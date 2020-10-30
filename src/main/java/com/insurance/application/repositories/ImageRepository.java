package com.insurance.application.repositories;

import com.insurance.application.models.Image;

import java.util.List;

public interface ImageRepository {

    Image getById(int id);

    void create(Image image);

    void delete(int id);

    void update(int id);

}
