package com.example.backend_sigopel_v1.entity;

import com.example.backend_sigopel_v1.entity.security.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Tracking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "IDCLIENTE", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "IDESTADO_TRACKING", nullable = false)
    private EstadoTracking estadoTracking;

    @ManyToOne
    @JoinColumn(name = "IDUSUARIO", nullable = false)
    private Usuario usuario;


    @Column(name = "fecha_servicio", nullable = false)
    private LocalDate fechaServicio;

    @OneToMany(mappedBy = "tracking", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Detalle> detalles = new ArrayList<>();
}
