package com.example.backend_sigopel_v1.mapper;

import com.example.backend_sigopel_v1.dto.ChoferDTO;
import com.example.backend_sigopel_v1.entity.Chofer;
import com.example.backend_sigopel_v1.mapper.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChoferMapper extends BaseMapper<Chofer, ChoferDTO> {

    @Override
    @Mapping(source = "estadoGeneral.id", target = "estadoGeneralId")
    @Mapping(source = "estadoGeneral.nombre", target = "estadoGeneralNombre")
    ChoferDTO toDTO(Chofer entity);

    @Override
    @Mapping(target = "estadoGeneral", ignore = true)
    Chofer toEntity(ChoferDTO dto);
}
