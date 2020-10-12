package com.insurance.application.repositories;

import com.insurance.application.models.dtos.InitialInfoStringDto;

public interface InfoDtoRepository {

    void create(InitialInfoStringDto stringDto);

    void update(InitialInfoStringDto stringDto);

    void delete(InitialInfoStringDto stringDto);

    InitialInfoStringDto getById(int id);

    InitialInfoStringDto getByTokenValue(String tokenValue);
}
