package com.codev.domain.model;

import com.codev.domain.dto.form.TechnologyDTOForm;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "tb_technology")
public class Technology extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @Column(unique = true)
    private String name;

    @Column
    private String description;

    @Column
    private String documentationLink;

    public Technology(TechnologyDTOForm technologyDTOForm) {
        this.name = technologyDTOForm.getName();
        this.description = technologyDTOForm.getDescription();
        this.documentationLink = technologyDTOForm.getDocumentationLink();
    }

    public Technology() {}

    public Technology copyProperties(TechnologyDTOForm technologyDTOForm) {
        if (technologyDTOForm.getName() != null)
            this.name = technologyDTOForm.getName();
        if (technologyDTOForm.getDescription() != null)
            this.description = technologyDTOForm.getDescription();
        if (technologyDTOForm.getDocumentationLink() != null)
            this.documentationLink = technologyDTOForm.getDocumentationLink();
        return this;
    }
}
