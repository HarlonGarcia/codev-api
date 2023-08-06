package com.codev.domain.dto.view;

import com.codev.domain.model.Technology;
import lombok.Data;

import java.util.UUID;

@Data
public class TechnologyDTOView {

    private UUID id;

    private String name;

    private String description;

    private String documentationLink;

    public TechnologyDTOView(Technology technology){
        this.id = technology.getId();
        this.name = technology.getName();
        this.description = technology.getDescription();
        this.documentationLink = technology.getDocumentationLink();
    }

}
