package com.example.backend_sigopel_v1.mapper;

import com.example.backend_sigopel_v1.dto.AlmacenDTO;
import com.example.backend_sigopel_v1.entity.Almacen;
import com.example.backend_sigopel_v1.mapper.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlmacenMapper extends BaseMapper<Almacen, AlmacenDTO> {
}
