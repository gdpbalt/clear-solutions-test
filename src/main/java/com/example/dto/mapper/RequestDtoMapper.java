package com.example.dto.mapper;

public interface RequestDtoMapper<D, T> {
    T toModel(D dto);

    T toModel(T entity, D dto);
}
