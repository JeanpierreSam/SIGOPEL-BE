package com.example.backend_sigopel_v1.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleDTO {
    private Long id;
    private Long vehiculoId;
    private String vehiculoPlaca;
    private Long choferId;
    private String choferNombre;
    private Long tiendaId;
    private String tiendaRazonSocial;
    private String tiendaDireccion;
    private Long almacenId;
    private String almacenNombre;
    private Long estadoEntregaId;
    private String estadoEntregaNombre;
    private String ventanaHorariaInicio;
    private String ventanaHorariaFin;
    private String observacion;
    private BigDecimal volumen;
}
