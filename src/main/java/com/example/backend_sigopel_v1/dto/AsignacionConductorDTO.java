package com.example.backend_sigopel_v1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsignacionConductorDTO {
    private List<AsignacionVehiculoConductor> asignaciones;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AsignacionVehiculoConductor {
        private String vehiculoPlaca;
        private Long choferId;
        private Integer numeroEntregas; // Info para mostrar en frontend
    }
}
