package com.codev.domain.service;

import com.codev.domain.dto.form.TechnologyDTOForm;
import com.codev.domain.exceptions.global.UniqueConstraintViolationException;
import com.codev.domain.model.Technology;
import com.codev.domain.repository.TechnologyRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class TechnologyService {

    private final TechnologyRepository technologyRepository;


    public List<Technology> findAllTechnologies() {
        return technologyRepository.findAllTechnologies();
    }

    @Transactional
    public Technology createTechnology(TechnologyDTOForm technologyDTOForm) throws UniqueConstraintViolationException {
        boolean technologyExists = technologyRepository.existsByName(technologyDTOForm.getName());
        if (technologyExists)
            throw new UniqueConstraintViolationException("createTechnology.technologyDTOForm.name");

        Technology technology = new Technology(technologyDTOForm);

        technology.persist();
        return technology;
    }

    @Transactional
    public Technology updateTechnology(UUID technologyId, TechnologyDTOForm technologyDTOForm) {
        Technology technology = Technology.findById(technologyId);
        if (technology == null)
            throw new EntityNotFoundException(String.format("Technology with id %s does not exist and therefore it was not possible to update", technologyId));

        technology = technology.copyProperties(technologyDTOForm);
        technology.persist();
        return technology;
    }

    @Transactional
    public void deleteTechnology(UUID technologyId) {
        Technology technology = Technology.findById(technologyId);
        if (technology == null)
            throw new EntityNotFoundException(String.format("Technology with id %s does not exist and therefore it was not possible to delete", technologyId));

        technologyRepository.deleteTechnology(technologyId);
    }
}
