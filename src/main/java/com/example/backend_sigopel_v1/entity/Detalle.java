package com.example.backend_sigopel_v1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Detalle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Detalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idtracking", nullable = false)
    private Tracking tracking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idvehiculo", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idchofer")
    private Chofer chofer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idtienda", nullable = false)
    private Tienda tienda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idalmacen", nullable = false)
    private Almacen almacen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idestado_entrega", nullable = false)
    private EstadoEntrega estadoEntrega;

    @Column(name = "ventana_horaria_inicio", nullable = false, length = 8)
    private String ventanaHorariaInicio;

    @Column(name = "ventana_horaria_fin", nullable = false, length = 8)
    private String ventanaHorariaFin;

    @Column(length = 255)
    private String observacion;

    @Column(precision = 10, scale = 2)
    private BigDecimal volumen;
}
