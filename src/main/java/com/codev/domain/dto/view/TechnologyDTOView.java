package com.codev.domain.dto.view;

import com.codev.domain.model.Technology;
import lombok.Data;

import java.util.UUID;

@Data
public class TechnologyDTOView {

    private UUID id;

    private String name;

    private String documentationLink;

    private String color;

    public TechnologyDTOView(Technology technology) {
        this.id = technology.getId();
        this.name = technology.getName();
        this.documentationLink = technology.getDocumentationLink();
        this.color = technology.getColor();
    }

}
