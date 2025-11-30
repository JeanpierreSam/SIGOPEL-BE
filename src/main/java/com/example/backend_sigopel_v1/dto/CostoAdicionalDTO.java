package com.example.backend_sigopel_v1.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CostoAdicionalDTO {
    private Long id;
    private Long detalleId;
    private Long tipoCostoId;
    private String tipoCostoNombre;
    private BigDecimal monto;
    private String descripcion;
}
