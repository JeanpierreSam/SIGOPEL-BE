package com.example.backend_sigopel_v1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Estado_General")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoGeneral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String nombre;
}
