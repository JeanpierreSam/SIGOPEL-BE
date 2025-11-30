package com.example.backend_sigopel_v1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehiculoEntregasDTO {
    private String placa;
    private Integer numeroEntregas;
    private Long choferIdActual; // null si no tiene chofer asignado
    private String choferNombreActual;
}
