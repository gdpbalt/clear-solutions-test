package com.example.dto.mapper;

public interface ResponseDtoMapper<D, T> {
    D toDto(T t);
}
