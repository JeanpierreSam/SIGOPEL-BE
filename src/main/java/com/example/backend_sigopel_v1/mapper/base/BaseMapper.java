package com.example.backend_sigopel_v1.mapper.base;

import java.util.List;

public interface BaseMapper<E,DTO> {
    DTO toDTO(E entity);
    E toEntity(DTO dto);
    List<DTO> toDTOList(List<E> entities);
    List<E> toEntityList(List<DTO> dtos);
}
