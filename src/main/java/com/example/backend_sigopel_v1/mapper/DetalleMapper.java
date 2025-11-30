// DetalleMapper.java
package com.example.backend_sigopel_v1.mapper;

import com.example.backend_sigopel_v1.dto.DetalleDTO;
import com.example.backend_sigopel_v1.entity.Detalle;
import com.example.backend_sigopel_v1.mapper.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DetalleMapper extends BaseMapper<Detalle, DetalleDTO> {

    @Override
    @Mapping(source = "vehiculo.id", target = "vehiculoId")
    @Mapping(source = "vehiculo.placa", target = "vehiculoPlaca")
    @Mapping(source = "chofer.id", target = "choferId")
    @Mapping(source = "chofer.nombre", target = "choferNombre")
    @Mapping(source = "tienda.id", target = "tiendaId")
    @Mapping(source = "tienda.razonSocial", target = "tiendaRazonSocial")
    @Mapping(source = "tienda.direccion", target = "tiendaDireccion")
    @Mapping(source = "almacen.id", target = "almacenId")
    @Mapping(source = "almacen.nombre", target = "almacenNombre")
    @Mapping(source = "estadoEntrega.id", target = "estadoEntregaId")
    @Mapping(source = "estadoEntrega.nombre", target = "estadoEntregaNombre")
    DetalleDTO toDTO(Detalle entity);

    @Override
    @Mapping(target = "tracking", ignore = true)
    @Mapping(target = "vehiculo", ignore = true)
    @Mapping(target = "chofer", ignore = true)
    @Mapping(target = "tienda", ignore = true)
    @Mapping(target = "almacen", ignore = true)
    @Mapping(target = "estadoEntrega", ignore = true)
    Detalle toEntity(DetalleDTO dto);
}
