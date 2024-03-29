package me.dio.devweek.application;

import me.dio.devweek.domain.model.Champion;
import me.dio.devweek.domain.repositories.ChampionsRepository;

import java.util.List;

public record ListChampionsUseCase(ChampionsRepository repository) {
    public List<Champion> findAll(){
        return repository.findAll();
    }
}
