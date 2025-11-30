// src/main/java/com/example/backend_sigopel_v1/service/general/service/VehiculoService.java
package com.example.backend_sigopel_v1.service.general.service;

import com.example.backend_sigopel_v1.dto.VehiculoDTO;
import com.example.backend_sigopel_v1.entity.Vehiculo;
import com.example.backend_sigopel_v1.service.base.GenericService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface VehiculoService extends GenericService<Vehiculo, VehiculoDTO, Long> {

    List<VehiculoDTO> buscarVehiculosConFiltros(
            BigDecimal capacidad,
            String estadoNombre,
            LocalDate fechaSoat
    );

    // Nuevo método para RF04
    byte[] exportarVehiculosAPdf(BigDecimal capacidad, String estadoNombre, LocalDate fechaSoat);
}