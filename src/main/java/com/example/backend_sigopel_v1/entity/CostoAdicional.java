package com.example.backend_sigopel_v1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "COSTO_ADICIONAL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CostoAdicional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCOSTO_ADICIONAL")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "IDDETALLE", nullable = false)
    private Detalle detalle;

    @ManyToOne
    @JoinColumn(name = "IDTIPO_COSTO", nullable = false)
    private TipoCosto tipoCosto;

    @Column(name = "MONTO", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(name = "DESCRIPCION", nullable = false, length = 255)
    private String descripcion;
}
