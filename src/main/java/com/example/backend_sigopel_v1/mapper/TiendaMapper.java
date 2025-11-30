// TiendaMapper.java
package com.example.backend_sigopel_v1.mapper;

import com.example.backend_sigopel_v1.dto.TiendaDTO;
import com.example.backend_sigopel_v1.entity.Tienda;
import com.example.backend_sigopel_v1.mapper.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TiendaMapper extends BaseMapper<Tienda, TiendaDTO> {

    @Override
    @Mapping(source = "distrito.codDist", target = "codigoDistrito")
    @Mapping(source = "distrito.nombreDist", target = "nombreDistrito")
    TiendaDTO toDTO(Tienda entity);

    @Override
    @Mapping(target = "distrito", ignore = true)
    Tienda toEntity(TiendaDTO dto);
}
