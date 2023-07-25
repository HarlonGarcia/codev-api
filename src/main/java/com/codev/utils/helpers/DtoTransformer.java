package com.codev.utils.helpers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

public class DtoTransformer<T, U> {
    private ModelMapper modelMapper = new ModelMapper();

    public List<U> transformToDTOList(List<T> entityList, Class<U> dtoClass) {
        return entityList.stream()
                .map(entity -> convertToDTO(entity, dtoClass))
                .collect(Collectors.toList());
    }

    private U convertToDTO(T entity, Class<U> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

}
