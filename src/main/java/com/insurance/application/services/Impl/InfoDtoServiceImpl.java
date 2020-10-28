package com.insurance.application.services.Impl;

import com.insurance.application.models.dtos.InitialInfoStringDto;
import com.insurance.application.repositories.InfoDtoRepository;
import com.insurance.application.services.InfoDtoService;
import org.springframework.stereotype.Service;

@Service
public class InfoDtoServiceImpl implements InfoDtoService {

    private InfoDtoRepository repository;

    public InfoDtoServiceImpl(InfoDtoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(InitialInfoStringDto stringDto) {
        repository.create(stringDto);
    }

    @Override
    public void update(InitialInfoStringDto stringDto) {
        repository.update(stringDto);
    }

    @Override
    public void delete(InitialInfoStringDto stringDto) {
        repository.delete(stringDto);
    }

    @Override
    public InitialInfoStringDto getById(int id) {
        return repository.getById(id);
    }

    @Override
    public InitialInfoStringDto getByTokenValue(String tokenValue) {
        return repository.getByTokenValue(tokenValue);
    }
}
