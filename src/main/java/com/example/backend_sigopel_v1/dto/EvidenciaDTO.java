package com.example.backend_sigopel_v1.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvidenciaDTO {
    private Long id;
    private Long detalleId;
    private String tipoEvidencia;
    private String rutaArchivo;
    private String descripcion;
}
