package me.dio.devweek.domain.repositories;

import me.dio.devweek.domain.model.Champion;

import java.util.List;
import java.util.Optional;

public interface ChampionsRepository {
    List<Champion> findAll();
    Optional<Champion> findById(Long Id);
}
