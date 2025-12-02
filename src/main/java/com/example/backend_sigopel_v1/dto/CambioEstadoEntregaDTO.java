// CambioEstadoEntregaDTO.java
package com.example.backend_sigopel_v1.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CambioEstadoEntregaDTO {
    private String estadoNombre;   // obligatorio
    private String observacion;    // opcional
}
