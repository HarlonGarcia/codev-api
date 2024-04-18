package com.codev.domain.service;

import com.codev.domain.dto.form.TechnologyDTOForm;
import com.codev.domain.exceptions.global.ErrorResponse;
import com.codev.domain.exceptions.global.UniqueConstraintViolationException;
import com.codev.domain.exceptions.global.Violation;
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
        if (technologyExists) {
            Violation violation = new Violation("createTechnology.technologyDTOForm.name",
                    "Unique constraint violation on a field that must be unique.");

            ErrorResponse errorResponse = new ErrorResponse(400, "Unique Constraint Violation", violation);

            throw new UniqueConstraintViolationException(errorResponse);
        }

        Technology technology = new Technology(technologyDTOForm);

        technology.persist();
        return technology;
    }

    @Transactional
    public Technology updateTechnology(UUID technologyId, TechnologyDTOForm technologyDTOForm) {
        Technology technology = Technology.findById(technologyId);
        if (technology == null)
            throw new EntityNotFoundException("Technology does not exist and therefore it was not possible to delete");

        technology = technology.copyProperties(technologyDTOForm);
        technology.persist();
        return technology;
    }

    @Transactional
    public void deleteTechnology(UUID technologyId) {
        Technology technology = Technology.findById(technologyId);
        if (technology == null)
            throw new EntityNotFoundException("Technology does not exist and therefore it was not possible to delete");

        technologyRepository.deleteTechnology(technologyId);
    }
}
