// src/main/java/com/example/backend_sigopel_v1/dto/VehiculoDTO.java
package com.example.backend_sigopel_v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoDTO {
    private Long id;
    private String placa;
    private String marca;
    private String modelo;
    private BigDecimal capacidad;

    // Atributos de la relación con EstadoVehiculo
    private Long estadoVehiculoId;
    private String estadoVehiculoNombre; // Esto es el 'tipo_estado'

    // Atributo para el SOAT (la fecha de vencimiento más reciente)
    private LocalDate vencimientoSoat;
}