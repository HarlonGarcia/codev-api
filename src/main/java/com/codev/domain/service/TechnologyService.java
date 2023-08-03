package com.codev.domain.service;

import com.codev.domain.dto.form.TechnologyDTOForm;
import com.codev.domain.model.Technology;
import com.codev.domain.repository.TechnologyRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TechnologyService {

    @Inject
    TechnologyRepository technologyRepository;

    @Transactional
    public Technology createTechnology(TechnologyDTOForm technologyDTOForm) {
        Technology technology = new Technology(technologyDTOForm);
        technology.persist();
        return technology;
    }

    @Transactional
    public Technology updateTechnology(Long technologyId, TechnologyDTOForm technologyDTOForm) {
        Technology technology = Technology.findById(technologyId);
        if (technology == null)
            throw new EntityNotFoundException("Technology does not exist and therefore it was not possible to delete");

        technology = technology.copyProperties(technologyDTOForm);
        technology.persist();
        return technology;
    }

    @Transactional
    public void deleteTechnology(Long technologyId) {
        Technology technology = Technology.findById(technologyId);
        if (technology == null)
            throw new EntityNotFoundException("Technology does not exist and therefore it was not possible to delete");

        technologyRepository.deleteTechnology(technologyId);
    }
}
