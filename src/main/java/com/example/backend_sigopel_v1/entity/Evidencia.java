package com.example.backend_sigopel_v1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "EVIDENCIA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDEVIDENCIA")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "IDDETALLE", nullable = false)
    private Detalle detalle;

    @Column(name = "TIPO_EVIDENCIA", length = 50)
    private String tipoEvidencia;

    @Column(name = "RUTA_ARCHIVO", nullable = false, length = 255)
    private String rutaArchivo;

    @Column(name = "DESCRIPCION", nullable = false, length = 255)
    private String descripcion;

    @Column(name = "FECHA_REGISTRO")
    private LocalDateTime fechaRegistro;
}
