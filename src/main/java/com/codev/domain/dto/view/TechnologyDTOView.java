package com.codev.domain.dto.view;

import com.codev.domain.model.Technology;
import com.codev.utils.helpers.DtoTransformer;
import lombok.Data;

import java.util.UUID;

@Data
public class TechnologyDTOView {

    private UUID id;

    private String name;

    private String documentationLink;

    private String color;

    public TechnologyDTOView(Technology technology) {
        DtoTransformer<Technology, TechnologyDTOView> transform = new DtoTransformer<>();
        transform.copyProperties(technology, this);
    }

    public TechnologyDTOView() {}

}
