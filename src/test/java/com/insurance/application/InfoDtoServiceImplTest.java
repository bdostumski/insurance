package com.insurance.application;

import com.insurance.application.models.dtos.InitialInfoStringDto;
import com.insurance.application.repositories.Impl.InfoDtoRepositoryImpl;
import com.insurance.application.repositories.InfoDtoRepository;
import com.insurance.application.services.Impl.InfoDtoServiceImpl;
import com.insurance.application.services.InfoDtoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InfoDtoServiceImplTest {

    InfoDtoRepository mokRepository = Mockito.mock(InfoDtoRepositoryImpl.class);
    InfoDtoService mokModelService = new InfoDtoServiceImpl(mokRepository);

    @Test
    public void getById_Should_Return_InfoDto() {
        // Arrange
        InitialInfoStringDto dto = new InitialInfoStringDto();
        dto.setId(1);

        Mockito.when(mokRepository.getById(1)).thenReturn(dto);

        // Act
        InitialInfoStringDto result = mokModelService.getById(1);

        // Assert
        Assertions.assertEquals(1, result.getId());
    }

    @Test
    public void create_Should_InfoDto() {
        // Arrange
        InitialInfoStringDto dto = new InitialInfoStringDto();
        Mockito.when(mokRepository.getById(1)).thenReturn(dto);

        //Act
        mokModelService.create(dto);

        // Assert
        Mockito.verify(mokRepository,
                Mockito.times(1)).create(dto);
    }

    @Test
    public void update_Should_Update_InfoDto() {
        // Arrange
        InitialInfoStringDto dto = new InitialInfoStringDto();
        Mockito.when(mokRepository.getById(2)).thenReturn(dto);

        //Act
        mokModelService.create(dto);
        dto.setId(2);
        mokModelService.update(dto);

        // Assert
        Mockito.verify(mokRepository,
                Mockito.times(1)).update(dto);
    }


    @Test
    public void delete_Should_Delete_InfoDto() {

        // Arrange
        InitialInfoStringDto dto = new InitialInfoStringDto();
        Mockito.when(mokRepository.getById(2)).thenReturn(dto);

        //Act
        mokModelService.create(dto);
        mokModelService.delete(dto);

        // Assert
        Mockito.verify(mokRepository,
                Mockito.times(1)).delete(dto);
    }
}
