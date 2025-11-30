package com.example.backend_sigopel_v1.mapper;
import com.example.backend_sigopel_v1.dto.UsuarioDTO;
import com.example.backend_sigopel_v1.entity.security.Usuario;
import com.example.backend_sigopel_v1.mapper.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper extends BaseMapper<Usuario, UsuarioDTO> {

    @Override
    @Mapping(target = "rol", expression = "java(entity.getRol().getNombre())")
    UsuarioDTO toDTO(Usuario entity);

    @Override
    @Mapping(target = "rol", ignore = true)  // se asigna en el servicio
    Usuario toEntity(UsuarioDTO dto);
}

