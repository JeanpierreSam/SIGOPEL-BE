package com.example.backend_sigopel_v1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Chofer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chofer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(nullable = false, unique = true, length = 8)
    private String dni;

    @Column(nullable = false, unique = true, length = 9)
    private String telefono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado_general", nullable = false)
    private EstadoGeneral estadoGeneral;
}
