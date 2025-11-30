package com.example.backend_sigopel_v1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Distrito")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Distrito {
    @Id
    @Column(name = "cod_dist", length = 6, columnDefinition = "CHAR(6)")
    private String codDist;

    @Column(name = "nombre_dist", nullable = false, length = 50)
    private String nombreDist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_prov", nullable = false)
    private Provincia provincia;
}
