// src/main/java/com/example/backend_sigopel_v1/mapper/VehiculoMapper.java
package com.example.backend_sigopel_v1.mapper;

import com.example.backend_sigopel_v1.dto.VehiculoDTO;
import com.example.backend_sigopel_v1.entity.Vehiculo;
import com.example.backend_sigopel_v1.entity.VehiculoSoat;
import com.example.backend_sigopel_v1.mapper.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface VehiculoMapper extends BaseMapper<Vehiculo, VehiculoDTO> {

    // Método personalizado para mapear la entidad a DTO
    @Mapping(source = "estadoVehiculo.id", target = "estadoVehiculoId")
    @Mapping(source = "estadoVehiculo.tipo_estado", target = "estadoVehiculoNombre")
    @Mapping(target = "vencimientoSoat", expression = "java(getVencimientoSoatMasReciente(entity))")
    VehiculoDTO toDTO(Vehiculo entity);

    // Método de utilidad para obtener la fecha de vencimiento más reciente del SOAT
    default LocalDate getVencimientoSoatMasReciente(Vehiculo vehiculo) {
        return Optional.ofNullable(vehiculo.getVehiculosSoat())
                .flatMap(lista -> lista.stream()
                        .max(Comparator.comparing(VehiculoSoat::getFecha_fin))
                        .map(VehiculoSoat::getFecha_fin))
                .orElse(null);
    }

    // Método para la conversión de DTO a Entidad (ignoramos los campos de relaciones)
    @Mapping(target = "estadoVehiculo", ignore = true)
    @Mapping(target = "vehiculosSoat", ignore = true)
    Vehiculo toEntity(VehiculoDTO dto);
}