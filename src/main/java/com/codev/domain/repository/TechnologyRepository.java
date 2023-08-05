package com.codev.domain.repository;

import java.util.List;

import com.codev.domain.model.Technology;

public interface TechnologyRepository {

    List<Technology> findAllTechnologies();
    void deleteTechnology(Long technologyId);

}
