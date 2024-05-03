package com.codev.domain.model;

import com.codev.domain.dto.form.TechnologyDTOForm;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "tb_technology")
public class Technology extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "documentation_link", length = 150)
    private String documentationLink;

    @Column(length = 6)
    private String color;

    public Technology(TechnologyDTOForm technologyDTOForm) {
        this.name = technologyDTOForm.getName();
        this.documentationLink = technologyDTOForm.getDocumentationLink();
        this.color = technologyDTOForm.getColor();
    }

    public Technology() {}

    public Technology copyProperties(TechnologyDTOForm technologyDTOForm) {
        if (technologyDTOForm.getName() != null)
            this.name = technologyDTOForm.getName();
        if (technologyDTOForm.getDocumentationLink() != null)
            this.documentationLink = technologyDTOForm.getDocumentationLink();
        return this;
    }
}
