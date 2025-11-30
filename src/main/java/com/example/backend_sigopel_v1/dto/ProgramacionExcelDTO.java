package com.example.backend_sigopel_v1.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramacionExcelDTO {
    private List<FilaProgramacion> filas;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FilaProgramacion {
        // Vehículo asignado
        private String vehiculoPlaca;  // ← AGREGADO

        // Tienda destino
        private String tiendaRazonSocial;
        private String tiendaDireccion;

        // Almacén origen
        private String almacenNombre;

        // Horario
        private String ventanaHorariaInicio;  // Ej: "08:00"
        private String ventanaHorariaFin;     // Ej: "12:00"

        // Carga
        private BigDecimal volumen;
        private String observacion;
    }
}
