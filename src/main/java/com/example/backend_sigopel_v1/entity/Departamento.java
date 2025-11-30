package com.example.backend_sigopel_v1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Departamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Departamento {
    @Id
    @Column(name = "cod_dep", length = 2, columnDefinition = "CHAR(2)")
    private String codDep;

    @Column(name = "nombre_dep", nullable = false, length = 50)
    private String nombreDep;
}
