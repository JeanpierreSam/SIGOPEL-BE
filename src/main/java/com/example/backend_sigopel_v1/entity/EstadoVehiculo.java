// src/main/java/com/example/backend_sigopel_v1/entity/EstadoVehiculo.java
package com.example.backend_sigopel_v1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Estado_Vehiculo") // Nombre de la tabla en la BD
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoVehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_estado", nullable = false, unique = true, length = 20)
    private String tipo_estado;
}