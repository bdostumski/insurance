package com.insurance.application;

import com.insurance.application.models.Image;
import com.insurance.application.repositories.ImageRepository;
import com.insurance.application.repositories.Impl.ImageRepositoryImpl;
import com.insurance.application.services.ImageService;
import com.insurance.application.services.Impl.ImageServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ImageServiceImplTest {

    ImageRepository mokRepository = Mockito.mock(ImageRepositoryImpl.class);
    ImageService mokModelService = new ImageServiceImpl(mokRepository);

    @Test
    public void getById_Should_Return_Image() {
        // Arrange
        Image img = new Image();
        img.setId(1);

        Mockito.when(mokRepository.getById(1)).thenReturn(img);

        // Act
        Image image = mokModelService.getById(1);

        // Assert
        Assertions.assertEquals(1, image.getId());
    }

    @Test
    public void create_Should_Image() {
        // Arrange
        Image img = new Image();
        Mockito.when(mokRepository.getById(1)).thenReturn(img);

        //Act
        mokRepository.create(img);

        // Assert
        Mockito.verify(mokRepository,
                Mockito.times(1)).create(img);
    }

    @Test
    public void update_Should_UpdateImage() {
        // Arrange
        Image img = new Image();
        Mockito.when(mokRepository.getById(2)).thenReturn(img);

        //Act
        mokModelService.create(img);
        img.setId(2);
        mokModelService.update((int)img.getId());

        // Assert
        Mockito.verify(mokRepository,
                Mockito.times(1)).update((int) img.getId());
    }

    @Test
    public void delete_Should_DeleteImage() {

        // Arrange
        Image img = new Image();
        Mockito.when(mokRepository.getById(1)).thenReturn(img);

        // Act
        mokModelService.create(img);
        mokModelService.delete((int) img.getId());

        // Assert
        Mockito.verify(mokRepository,
                Mockito.times(1)).delete((int) img.getId());
    }
}
