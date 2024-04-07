package com.codev.domain.repository;

import java.util.List;
import java.util.UUID;

import com.codev.domain.model.Technology;

public interface TechnologyRepository {

    List<Technology> findAllTechnologies();
    void deleteTechnology(UUID technologyId);
    boolean existsByName(String name);
}
