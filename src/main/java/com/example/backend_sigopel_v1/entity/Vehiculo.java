// src/main/java/com/example/backend_sigopel_v1/entity/Vehiculo.java
package com.example.backend_sigopel_v1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Vehiculo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "placa", nullable = false, unique = true, length = 7)
    private String placa;

    @Column(name = "marca", nullable = false, length = 20)
    private String marca;

    @Column(name = "modelo", nullable = false, length = 20)
    private String modelo;

    @Column(name = "capacidad", nullable = false, precision = 10, scale = 2)
    private BigDecimal capacidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado_vehiculo", nullable = false)
    private EstadoVehiculo estadoVehiculo;

    @OneToMany(mappedBy = "vehiculo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<VehiculoSoat> vehiculosSoat;
}