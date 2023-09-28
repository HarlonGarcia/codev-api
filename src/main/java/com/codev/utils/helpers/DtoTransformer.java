package com.codev.utils.helpers;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class DtoTransformer<T, U> {
    private final ModelMapper modelMapper;

    public DtoTransformer() {
        this.modelMapper = new ModelMapper();
    }

    public List<U> transformToDTOList(List<T> entityList, Class<U> dtoClass) {
        return entityList.stream()
                .map(entity -> convertToDTO(entity, dtoClass))
                .collect(Collectors.toList());
    }

    private U convertToDTO(T entity, Class<U> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public void copyProperties(T entity, U dto) {
        modelMapper.map(entity, dto);
    }

}
