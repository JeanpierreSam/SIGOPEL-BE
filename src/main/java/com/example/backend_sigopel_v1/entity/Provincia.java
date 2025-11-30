package com.example.backend_sigopel_v1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Provincia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Provincia {
    @Id
    @Column(name = "cod_prov", length = 4, columnDefinition = "CHAR(4)")
    private String codProv;

    @Column(name = "nombre_prov", nullable = false, length = 50)
    private String nombreProv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_dep", nullable = false)
    private Departamento departamento;
}
