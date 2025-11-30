package com.example.backend_sigopel_v1.mapper;

import com.example.backend_sigopel_v1.dto.ClienteDTO;
import com.example.backend_sigopel_v1.entity.Cliente;
import com.example.backend_sigopel_v1.mapper.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper extends BaseMapper<Cliente, ClienteDTO> {

    @Override
    @Mapping(source = "estadoGeneral.id", target = "estadoGeneralId")
    @Mapping(source = "estadoGeneral.nombre", target = "estadoGeneralNombre")
    ClienteDTO toDTO(Cliente entity);

    @Override
    @Mapping(target = "estadoGeneral", ignore = true)
    Cliente toEntity(ClienteDTO dto);
}
