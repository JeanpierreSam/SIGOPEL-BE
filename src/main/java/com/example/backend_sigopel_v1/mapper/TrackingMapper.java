// TrackingMapper.java
package com.example.backend_sigopel_v1.mapper;

import com.example.backend_sigopel_v1.dto.TrackingDTO;
import com.example.backend_sigopel_v1.entity.Tracking;
import com.example.backend_sigopel_v1.mapper.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DetalleMapper.class})
public interface TrackingMapper extends BaseMapper<Tracking, TrackingDTO> {

    @Mapping(source = "clienteId",        target = "cliente.id")
    @Mapping(source = "estadoTrackingId", target = "estadoTracking.id")
    @Mapping(source = "usuarioId",        target = "usuario.id")
    Tracking toEntity(TrackingDTO dto);

    @Mapping(source = "cliente.id",        target = "clienteId")
    @Mapping(source = "estadoTracking.id", target = "estadoTrackingId")
    @Mapping(source = "usuario.id",        target = "usuarioId")
    @Mapping(source = "cliente.razonSocial",      target = "clienteRazonSocial")
    @Mapping(source = "estadoTracking.nombre",    target = "estadoTrackingNombre")
    @Mapping(source = "usuario.username",         target = "usuarioUsername")
    TrackingDTO toDTO(Tracking entity);

    java.util.List<TrackingDTO> toDTOList(java.util.List<Tracking> list);
}
