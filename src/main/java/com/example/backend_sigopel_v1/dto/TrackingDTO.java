package com.example.backend_sigopel_v1.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackingDTO {
    private Long id;
    private Long clienteId;
    private Long estadoTrackingId;
    private Long usuarioId;

    private String clienteRazonSocial;
    private String estadoTrackingNombre;
    private String usuarioUsername;
    private LocalDate fechaServicio;
    private List<DetalleDTO> detalles;
}
