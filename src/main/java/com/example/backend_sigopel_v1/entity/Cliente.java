package com.example.backend_sigopel_v1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 11)
    private String ruc;

    @Column(name = "razon_social", nullable = false, length = 100)
    private String razonSocial;

    @Column(name = "representante_legal", nullable = false, length = 100)
    private String representanteLegal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado_general", nullable = false)
    private EstadoGeneral estadoGeneral;
}
