package com.example.backend_sigopel_v1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Almacen")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Almacen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;
}
