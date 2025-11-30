// src/main/java/com/example/backend_sigopel_v1/entity/Soat.java
package com.example.backend_sigopel_v1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Soat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Soat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;
}