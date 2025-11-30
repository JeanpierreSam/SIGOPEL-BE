package com.example.backend_sigopel_v1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Tienda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tienda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "razon_social", nullable = false, length = 100)
    private String razonSocial;

    @Column(nullable = false, length = 200)
    private String direccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_dist", nullable = false)
    private Distrito distrito;
}
